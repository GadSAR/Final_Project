package com.backend.ifm.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class ExecuteScriptService {

    public void executeScript(int modelId, String action) {
        String userHomeDirectory = System.getProperty("user.desktop");
        String pythonFileDirectory = "\\College\\Second_year\\Final_Project\\IFM_v1\\poweredByAi\\scripts";
        String scriptName = "model" + modelId + "_" + action + ".py";
        String fetching = "python " + userHomeDirectory + pythonFileDirectory + scriptName;

        startModel(fetching);
    }

    public void startModel(String fetching) {
        String[] commandToExecute = new String[]{"cmd.exe", "/c", fetching};
        try {
            // give 5 seconds margin, to the script to finish (waitFor(5 seconds....)
            Runtime.getRuntime().exec(commandToExecute).waitFor(100, TimeUnit.SECONDS);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
