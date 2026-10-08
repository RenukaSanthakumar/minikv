package com.renuka;

import com.renuka.minikv.KeyValueStore;
import com.renuka.minikv.MiniKV;

import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kv")
@CrossOrigin(origins = "*")
public class MiniKVController {

    private final KeyValueStore kv = new MiniKV();

    // PUT /kv/name
    @PutMapping("/{key}")
    public String put(
            @PathVariable String key,
            @RequestBody String value) {

        kv.put(key, value);
        return "OK";
    }

    // GET /kv/name
    @GetMapping("/{key}")
    public String get(@PathVariable String key) {

        String value = kv.get(key);

        return value != null ? value : "null";
    }

    @GetMapping("/all")
    public Map<String, String> getAll() {
        
        return kv.getAll();
    }

    // DELETE /kv/name
    @DeleteMapping("/{key}")
    public String delete(@PathVariable String key) {

        kv.delete(key);
        return "OK";
    }

    // GET /kv
    @GetMapping
    public int size() {

        return kv.size();
    }
}