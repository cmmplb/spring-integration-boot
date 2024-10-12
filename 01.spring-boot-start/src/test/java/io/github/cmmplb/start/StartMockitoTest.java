package io.github.cmmplb.start;

import io.github.cmmplb.core.utils.RandomUtil;
import io.github.cmmplb.start.entity.User;
import io.github.cmmplb.start.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author penglibo
 * @date 2024-10-12 11:52:36
 * @since jdk 1.8
 * Mock 测试
 */

@Slf4j
@SpringBootTest(classes = StartApplication.class)
public class StartMockitoTest {

    // 由@Aotowired改为@Mock注入
    @Mock
    private UserService userService;

    @BeforeEach
    @DisplayName("初始化方法")
    public void init() {
        System.out.println("初始化方法");
    }

    @Test
    @DisplayName("测试Mock")
    public void getUser() {
        // 当调用 selectById 方法，并且任意整数参数时，就返回一个对象，对象中的属性我这里随机生成了。
        Mockito.when(userService.selectById(Mockito.anyInt())).thenReturn(new User(RandomUtil.getNum(1, 100), RandomUtil.getRandomChineseName()));
        log.info("anyInt:{}", userService.selectById(RandomUtil.getNum(1, 100)));

        // 当调用 selectById 方法，并且参数为3时，就返回一个对象，对象中的属性为老八。
        Mockito.when(userService.selectById(3)).thenReturn(new User(3, "老八"));
        log.info("selectById(3):{}", userService.selectById(3));
    }

    @Test
    @DisplayName("验证方法是否被调用过，并且调用的次数为1次")
    public void testVerify() {
        // 验证 selectById 是否被调用过1次
        userService.selectById(2);
        // Mockito.verify(userService, Mockito.times(1)).selectById(Mockito.anyInt());

        // 验证 selectById 是否被调用过1次，并且参数为3
        Mockito.verify(userService, Mockito.times(1)).selectById(Mockito.eq(3));

        // 验证方法是否至少被调用了一次=>Mockito.atLeastOnce()
        // 验证方法是否至多被调用了两次=>Mockito.atMost(2)


    }

    @Test
    @DisplayName("验证方法的调用顺序")
    public void verifyOrder() {
        userService.selectById(3);
        userService.selectById(5);
        userService.testVoid();

        // 验证调用顺序，是否先调用 selectById() 两次，并且第一次的参数是 3、第二次的参数是 5，然后才调用 testVoid() 方法
        InOrder inOrder = Mockito.inOrder(userService);
        inOrder.verify(userService).selectById(3);
        // inOrder.verify(userService).selectById(5);
        inOrder.verify(userService).selectById(1);
        inOrder.verify(userService).testVoid();
    }

    @Test
    @DisplayName("异常")
    public void doThrowTest() {
        // 当调用 selectById，输入的的参数是 9 时，抛出一个 RuntimeException
        // Mockito.doThrow(new RuntimeException("抛出异常1")).when(userService).selectById(9);
        // log.info("selectById(3):{}", userService.selectById(9));
        // 另一种写法
        Mockito.when(userService.selectById(9)).thenThrow(new RuntimeException("抛出异常2"));
        log.info("selectById(3):{}", userService.selectById(9));

    }

    @Test
    @DisplayName("对 void 的方法设置模拟")
    public void voidTest() {
        /*对void的方法设置模拟*/
        Mockito.doAnswer(invocationOnMock -> {
            log.info("进入了Mock");
            return null;
        }).when(userService).testVoid();

        // 该方法会调用 Mockito.doAnswer() 方法
        userService.testVoid();
    }

}
