package com.moscow.zoo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.moscow.zoo.service.ZooService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class AppController {

    final String jsonString = new ZooService().getJsonString();

    @GetMapping("/")
    public String welcomePage(Model model) {
        model.addAttribute("json", jsonString);
        return "welcome";
    }

    @SneakyThrows
    @GetMapping("/id")
    public String getZooById(@RequestParam String id, Model model) {
        JsonNode node = new ZooService().getNode(jsonString, id);

        model.addAttribute("json", node);
        model.addAttribute("kind", node.get("Kind").asText());
        model.addAttribute("info", node.get("Info").asText());
        model.addAttribute("cage_location", "Местоположение в Московском зоопарке: "
                + node.get("CageLocation").asText());
        model.addAttribute("photo",
                "https://op.mos.ru/MEDIA/showFile?id=" + node.get("Photo").asText());
        return "zoo";
    }

    @PostMapping("/find")
    public String findZoo(@RequestParam("inputValue") String value, Model model) {
        model.addAttribute("responseId", value);
        return "redirect:/id?id="+value;
    }

    @GetMapping("/id-list")
    public String getIdList(Model model) {
        model.addAttribute("ids", new ZooService().idList(jsonString));
        log.info(String.valueOf(new ZooService().idList(jsonString).size()));
        return "id_list";
    }

}
