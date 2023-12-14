package com.example.javataskinterview.bot;

import com.example.javataskinterview.services.TreeService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Data
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig config;
    private final TreeService treeService;
    @Autowired
    public TelegramBot(BotConfig config, TreeService treeService){
        this.treeService =treeService;
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }
    @Override
    public String getBotToken(){
        return config.getToken();
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String text = message.getText();

            if (text.startsWith("/addElement")) {
                handleAddElementCommand(message);
            }
            else if (text.equals("/viewTree")){
                sendTextMessage(message.getChatId(), treeService.viewTree());
            }
            else if (text.startsWith("/removeElement")) {
                handleRemoveElementCommand(message);
            }
            else if (text.equals("/help")){
                sendTextMessage(message.getChatId() , help());
            }
        }
    }
    private void handleAddElementCommand(Message message) {
        String[] commandParts = message.getText().split("\\s+");

        if (commandParts.length == 2) {
            // Command format: /addelement <name>
            String name = commandParts[1];
            treeService.addElement(name);
            sendTextMessage(message.getChatId(), "Element added: " + name);
        } else if (commandParts.length == 3) {
            // Command format: /addelement <parent> <name>
            String parentName = commandParts[1];
            String name = commandParts[2];
            treeService.addElement(parentName, name);
            sendTextMessage(message.getChatId(), "Element added under " + parentName + ": " + name);
        } else {
            sendTextMessage(message.getChatId(), "Invalid command format. Use /addelement <name> or /addelement <parent> <name>");
        }
    }

    private void sendTextMessage(Long chatId, String text) {
        SendMessage response = new SendMessage();
        response.setChatId(chatId.toString());
        response.setText(text);
        try {
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void handleRemoveElementCommand(Message message) {
        String[] commandParts = message.getText().split("\\s+");

        if (commandParts.length == 2) {
            // Command format: /removeelement <name>
            String name = commandParts[1];
            treeService.removeElement(name);
            sendTextMessage(message.getChatId(), "Element removed: " + name);
        } else {
            sendTextMessage(message.getChatId(), "Invalid command format. Use /removeelement <name>");
        }
    }
    public String help() {
        return "/viewTree - View the tree structure\n" +
                "/addElement <element name> - Add a new root element\n" +
                "/addElement <parent element> <child element name> - Add a new child element\n" +
                "/removeElement <element name> - Remove an element\n" +
                "/help - Display available commands";
    }
}
