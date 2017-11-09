package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

	@GetMapping("/")
    public String hello() {
        return "Hello Spring-Boot";
    }
	
	@GetMapping("/info")
    public Map<String, Object> getInfo(@RequestParam String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        return map;
    }

	@GetMapping("/listmap")
    public List<Map<String, String>> getListMap() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = null;
        for (int i = 1; i <= 5; i++) {
            map = new HashMap<>();
            map.put("name", "Shanhy-" + i);
            list.add(map);
        }
        return list;
    }
}
