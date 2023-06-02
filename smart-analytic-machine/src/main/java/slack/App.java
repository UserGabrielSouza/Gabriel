/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slack;

import java.io.IOException;
import org.json.JSONObject;

/**
 *
 * @author Alexa
 */
public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        JSONObject alertaCpu = new JSONObject();
        JSONObject alertaRam = new JSONObject();
        JSONObject alertaDisco = new JSONObject();
        JSONObject alertaRede = new JSONObject();
        
        //json.put("text", "Teste test :double_exclamation_mark: :cross_mark: :check_mark: "
        //        + ":check_mark_button: :red_exclamation_mark: :white_exclamation_mark:");
        //Slack.sendMessage(json);
        
        alertaCpu.put("text", "Cpu!!!");
        alertaRam.put("text", "Ram!!!");
        alertaDisco.put("text", "Disco !!!");
        alertaRede.put("text", "Rede!!!");
        
        Slack.sendMessage(alertaRam);
    }
}
