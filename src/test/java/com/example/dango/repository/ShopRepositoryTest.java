package com.example.dango.repository;

import com.example.dango.shop.entity.Shop;
import com.example.dango.shop.repository.ShopRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ShopRepositoryTest {
    @Autowired
    private ShopRepository shopRepository;

    @Test
    public void shopTest() {
        // given
        Shop shop = Shop.builder()
                .shopUid(1L)
                .build();
        // when
        shopRepository.save(shop);
        // then
        Shop ret = shopRepository.findByShopUid(1L).orElseThrow(() -> new RuntimeException("not found"));
        assertEquals(ret.getShopUid(), 1L);
        assertEquals(ret, shop);
    }
}
