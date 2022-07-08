package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class TradeController {
    // TODO: Inject Trade service

    Logger LOGGER = LogManager.getLogger(TradeController.class);

    @Autowired
    private TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model) {
        // TODO: find all Trade, add to model
        LOGGER.info("All Trades retrieved");
        model.addAttribute("allTrades", tradeService.getAllTrades());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        LOGGER.info("Getting the addTrade Form");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Trade list
        if (!result.hasErrors()) {
            tradeService.saveTrade(trade);
            LOGGER.info("Trade's successfully created !");
            model.addAttribute("allTrades", tradeService.getAllTrades());
            return "redirect:/trade/list";
        }
        LOGGER.error("Failed to create a new Trade");
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Trade by Id and to model then show to the form
        LOGGER.info("Getting the updateTrade Form");
        model.addAttribute("trade", tradeService.getTradeById(id));
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Trade and return Trade list
        if (result.hasErrors()) {
            LOGGER.error("Failed to update Trade");
            return "trade/update";
        } else {
            boolean updated = tradeService.updateTrade(id, trade);
            System.out.println(updated);
            if (updated) {
                LOGGER.info("Trade's successfully updated !");
                model.addAttribute("allTrades", tradeService.getAllTrades());
            }
            return "redirect:/trade/list";
        }
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Trade by Id and delete the Trade, return to Trade list
        tradeService.deleteTradeById(id);
        LOGGER.info("Trade's successfully deleted !");
        model.addAttribute("allTrades", tradeService.getAllTrades());
        return "redirect:/trade/list";
    }
}
