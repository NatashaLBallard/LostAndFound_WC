package com.lostandfound_wc.demo;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item,Long> {
    Iterable <Item> findAllByListTypeContainingIgnoreCase(String itemCategory);
}