package com.webank.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webank.constant.BuildToolsConstant;
import com.webank.constant.FileOperator;

/**
 * @author tonychen 2019/4/8
 */
public final class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static void writeToFile(
            String content,
            String fileName,
            FileOperator operator) {

        OutputStreamWriter ow = null;
        try {
            boolean flag = true;
            File file = new File(fileName);
            if (StringUtils.equals(operator.getAction(), FileOperator.OVERWRITE.getAction()) && file
                    .exists()) {
                flag = file.delete();
            }
            if (!flag) {
                logger.error("writeAddressToFile() delete file is fail.");
                return;
            }
            ow = new OutputStreamWriter(new FileOutputStream(fileName, true),
                    BuildToolsConstant.UTF_8);
            ow.write(content);
            ow.close();
        } catch (IOException e) {
            logger.error("writer file exception.", e);
        } finally {
            if (null != ow) {
                try {
                    ow.close();
                } catch (IOException e) {
                    logger.error("io close exception.", e);
                }
            }
        }
    }

    public static String readFile(String fileName) {

        File file = new File(fileName);
        if (!file.exists()) {
            return StringUtils.EMPTY;
        }
        InputStream in = null;
        int length = 0;
        byte[] data = new byte[1024 * 4];
        try {
            in = new FileInputStream(file);
            length = in.read(data);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new String(data, 0, length);
    }
}