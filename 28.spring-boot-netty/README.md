Netty 5.0/4.0新变化和注意点

核心改变:
- 简化 handler 类型继承关系
- channelRead0() → messageReceived()
- 更灵活的线程模型
- 更好的Channel.deregister(...)


简化 handler 类型继承关系
ChannelInboundHandler 和 ChannelOutboundHandler 被合并到 ChannelHandler. 现在ChannelHandler拥有inbound 和 outbound handler 方法.

ChannelInboundHandlerAdapter, ChannelOutboundHandlerAdapter, 和 ChannelDuplexHandlerAdapter 弃用了， 被 ChannelHandlerAdapter 取代.

因为现在你无法区分一个 handler是 inbound handler 或者 outbound handler, 所以CombinedChannelDuplexHandler 被 ChannelHandlerAppender取代.

想了解这个改变的更多信息，请看 pull request #1999.

channelRead0() → messageReceived()
我知道，这是一个傻傻的错误. 如果你正在使用SimpleChannelInboundHandler, 你不得不将channelRead0()重命名为messageReceived().

更灵活的线程模型
在Netty 4.x中， 每个EventLoop和一个固定的线程紧密耦合， 这个线程会执行它注册的channel的所有的I/O事件，以及指派给它的任务.
在5.0中， 一个EventLoop不再直接使用线程，而是使用一个 Executor. 也就是， 它使用一个 Executor 对象作为构造函数的参数，以前是在一个无尽的循环中拉取I/O事件， 吸纳在是每次迭代的结果是一个task，将此task提交给Executor执行.
如果没有特别指定，Executor默认使用 ForkJoinPool. ForkJoinPool使用thread-local 队列. 也就是说, 从线程A中提交到ForkJoinPool到非常可能再由线程A执行. 这提供了EventLoop高层次的thread affinity.

而且，程序员也可以提供他们自己的Executor (也叫做 thread pool) 调度 EventLoop. 一个场景可以证明它有用：当 Netty用作大规模的软件系统中. 假定此系统已经使用一个线程池并发地执行它的任务. Netty 4.x简单的产生大量的线程，完全不顾它是一个大规模系统的一部分. 自Netty 5.0起, 开发者可以运行 Netty 和系统的其它部分在同一个线程池中， 通过应用更好的调度策略和较少的调度开支可以潜在地提高性能. 细节讨论可以参照 GitHub issue 2250.

应该提到的是，这个改变不会影响ChannelHandlers的方式. 在开发者看来，唯一改变的是不再保证同一个ChannelHandler 会被同一个线程执行. 然而可以保证的是， 它不会同时被两个或者两个以上的线程执行.此外, Netty 会负责内存可见性的问题，所以不必担心线程安全性和 ChannelHandler的volatile变量.

这个改变的另一个影响就是 NioEventLoop, NioEventLoopGroup, EpollEventLoop 和 EpollEventLoopGroup 不再使用ThreadFactory作为构造函数的参数. 这些构造函数现在取而代之使用Executor 和 ExecutorFactory对象.

更好的Channel.deregister(...)
Netty 4.0引入了Channel.deregister(...), 5.0中它的行为被更新了以便符合Netty的线程模型.
现在可以保证在ChannelHandler中提交到EventLoop中的所有task在Channel取消注册(deregister)前都会被EventLoop执行
然而Channel.deregister(...)保留了非阻塞的操作，所以你不得不等待 返回的ChannelFuture成功后才能将它安全的注册到另一个EventLoop.

当调用Channel.deregister(...)后任何尝试在这个ChannelHandler中提交新的task (Runnable 或 Callable) 到会触发 RejectedExecutionException. 一旦这个 Channel注册到另外一个EventLoop, 一切归于正常.

ChannelHandler通过 EventLoop.schedule*(...)方法提交的task当Channel取消注册后会停止执行, 当Channel再次注册时这些task会自动移到新的EventLoop中继续执行. 这个限制只会影响在Channel取消注册时被调度的task. 那么delay的或者定期执行的task不受影响.

你也可以突破这个限制，尽管不被推荐. Netty 5.0 引入了一个新的方法 EventLoop.unwrap(), 它返回原始的EventLoop并不会执行一个健全的检查. 更准确的讲， 当提交task或者调度task到 "unwrapped" EventLoop时, 不会保证这些task会被并发执行，调度的task也不保证自动移到新的EventLoop.