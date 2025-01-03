package com.qyood.telerik.utils.files;

import com.qyood.telerik.utils.listenersAndloggers.Log4j2Manager;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class Files {
    private boolean internalInstance = false;

    public static Files getInstance() {
        return getInstance(false);
    }

    public static Files getInstance(boolean internalInstance) {
        var instance = new Files();
        instance.internalInstance = internalInstance;
        return instance;
    }

    public void deleteFolder(String folderPath) {
        deleteFile(folderPath);
    }

    public void deleteFile(String targetFilePath) {
        File targetFile;
        targetFile = new File(targetFilePath);
        boolean wasFileDeleted = FileUtils.deleteQuietly(targetFile);
        if (!wasFileDeleted) {
            targetFile = new File((new File(targetFilePath)).getAbsolutePath());
            wasFileDeleted = FileUtils.deleteQuietly(targetFile);
        }
        String negation = wasFileDeleted ? "" : "not ";
        Log4j2Manager.logInfoLogMessage("Target File Path: \"" + targetFilePath + "\", file was " + negation + "deleted.");
    }

}
