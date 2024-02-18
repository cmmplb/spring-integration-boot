package com.cmmplb.elasticsearch.controller;

import com.cmmplb.elasticsearch.entity.Article;
import com.cmmplb.elasticsearch.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-12-20 14:41:40
 * @since jdk 1.8
 */

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/init")
    public void init() {
        articleService.createIndex();
        List<Article> list = new ArrayList<>();
        for (int i = 0; i < 50000; i++) {
            list.add(new Article((long) i, "汤姆叔叔的小屋", "美国女作家斯托夫人在1852年出版的一部反映黑奴制度的作品，甫一面世，便掀起了美国废奴运动的高潮，对美国南北战争的爆发起到极大的推动。之后的100多年被译成多种文字，对世界人民反对种族歧视、争取自由民主产生了深远的影响。", 1));
            list.add(new Article((long) i + 1, "弗兰肯斯坦", "本书被誉为科幻小说的开山之作，其作者玛丽·雪莱也因此被称为科幻小说之母。《弗兰肯斯坦》创作于1818年，小说主角弗兰肯斯坦是生物学家，热衷于生命起源的探索，他经常出没于藏尸间，用不同尸体拼出了一个巨大人体怪物，这个怪物获得生命醒来，吓得弗兰肯斯坦落荒而逃，怪物对他紧追不舍...", 1));
            list.add(new Article((long) i + 2, "这个世界土崩瓦解了", "本书是英国布克文学奖经典名著，讲述主人公摔跤手奥贡喀沃，有三个妻子，两个装满木薯的仓房，而且即将成为氏族的领袖。然而，一场意外使奥贡喀沃遭到流放。七年后，奥贡喀沃返回家乡时，面临的是一个被新世界冲击的村庄，原来的庄子完全变了，所有人也都完全变了，世界再也回不到从前了，这个世界土崩瓦解了...", 1));
        }
        articleService.saveAll(list);
    }

    @GetMapping("/all")
    public Iterator<Article> all() {
        return articleService.findAll();
    }
}
