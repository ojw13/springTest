package com.ojw.utils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;
import net.sourceforge.tess4j.util.LoadLibs;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * tesseract for java， ocr（Optical Character Recognition，光学字符识别）
 * 工具类
 * @author wind
 */
public class Tess4jUtils {
    /**
     * 从图片中提取文字,默认设置英文字库,使用classpath目录下的训练库
     * @param path
     * @return
     */
    public static String readChar(String path){
        // JNA Interface Mapping
        ITesseract instance = new Tesseract();
        // JNA Direct Mapping
        // ITesseract instance = new Tesseract1();
        File imageFile = new File(path);
        //In case you don't have your own tessdata, let it also be extracted for you
        //这样就能使用classpath目录下的训练库了
        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        //Set the tessdata path
        instance.setDatapath(tessDataFolder.getAbsolutePath());
        //英文库识别数字比较准确
        instance.setLanguage("");
        return getOCRText(instance, imageFile);
    }

    /**
     * 从图片中提取文字
     * @param path 图片路径
     * @param dataPath 训练库路径
     * @param language 语言字库
     * @return
     */
    public static String readChar(String path, String dataPath, String language){
        File imageFile = new File(path);
        ITesseract instance = new Tesseract();
        instance.setDatapath(dataPath);
        //英文库识别数字比较准确
        instance.setLanguage(language);
        return getOCRText(instance, imageFile);
    }

    /**
     * 识别图片文件中的文字
     * @param instance
     * @param imageFile
     * @return
     */
    private static String getOCRText(ITesseract instance, File imageFile){
        String result = null;
        try {
            result = instance.doOCR(imageFile);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Test
    public void test() throws Exception {
        /*String path = "src/main/resources/image/text.png";
        System.out.println(readChar(path));*/

        String ch = "D:\\java\\testImg\\src\\main\\resources\\image\\ch54654.png";
        try {
            File imageFile = new File(ch);
            BufferedImage grayImage = ImageHelper.convertImageToBinary(ImageIO.read(imageFile));
            //            flex(1000,1000,grayImage,"D:\\java\\testImg\\src\\main\\resources\\image\\ch1asf.png");
//            BufferedImage image= toBufferedImage(grayImage.getScaledInstance(1000,1000,Image.SCALE_DEFAULT   ));
//            BufferedImage image2 = toBufferedImage(grayImage.getScaledInstance(grayImage.getWidth(),grayImage.getHeight(),Image.SCALE_DEFAULT   ));
            ImageIO.write(grayImage, "png", new File("D:\\java\\testImg\\src\\main\\resources\\image\\", "ch54654dd.png"));
            ITesseract instance = new Tesseract();   // JNA Interface Mapping
            instance.setDatapath("D:\\java\\testImg\\src\\main\\resources");//设置tessdata位置
            instance.setLanguage("eng");//选择字库文件（只需要文件名，不需要后缀名）
//            instance.setLanguage("chi_sim");//选择字库文件（只需要文件名，不需要后缀名）
            String result = instance.doOCR(imageFile);//开始识别
            System.out.println(result);//打印图片内容
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }

        image = new ImageIcon(image).getImage();


        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;

            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
        }

        if (bimage == null) {
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }

        Graphics g = bimage.createGraphics();

        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }


    /**
     *
     * @param width 改变后图片的宽
     * @param height 改变后图片的高
     * @param oldBufferedImage 改变后图片的高
     * @throws IOException
     */
    public static void flex(float width, float height, BufferedImage oldBufferedImage, String newFilePath) throws IOException{
        //1原来宽和高与新的宽高的比例K
        /**
         -------------------------------------------
         ******************
         ******************
         ******************
         ******************
         ******************
         把原来的图形像素填充到更大的区域里面去,分到大的空间，每一份的比例
         ********************************
         ********************************
         ********************************
         ********************************
         ********************************
         ********************************
         ********************************
         ---------------------------------------------
         */
        float kx = oldBufferedImage.getWidth()/width;//新的图片的宽占原来像素的大小
        float ky = oldBufferedImage.getHeight()/height;//新的图片的高占原来像素的大小

        int startX=0,startY=0,offset=0;//初始定义值，具体含义见上一篇blog
        int oldWidth = oldBufferedImage.getWidth();
        int scansize= oldWidth;//扫描间距
        int oldHeight = oldBufferedImage.getHeight();
        int newWidth = (int) (oldWidth/kx);//新图片宽
        int newHeight = (int) (oldHeight/ky);//新图片高
        //获取旧的像素数组
        int[] pix = new int[offset+(int) (oldHeight-startY)*scansize+(oldWidth-startX)];
        oldBufferedImage.getRGB(startX, startY, oldWidth, oldHeight, pix, offset, scansize);
        //新的像素数组定义

        int newStartX=0,newStartY=0,newOffset=0;
        int newScansize=newWidth;
        System.out.print(newScansize);
        int[] newPix = new int[newOffset+(int) (newHeight-newStartY)*newScansize+(newWidth-newStartX)];
        for(int x = 0;x<newWidth-startX;x++){//遍历每一个行
            for(int y=0; y<newHeight-startY;y++){//遍历每一个列
                newPix[newOffset+(y-newStartY)*newScansize+(x-newStartX)]  = pix[offset+((int)((y-startY)*ky))*scansize+(int)((x-startX)*kx)];
                //这里取值的时候记得用变量乘以头先算出来的比例
            }
        }
        BufferedImage imgOut = new BufferedImage(newWidth,newHeight,oldBufferedImage.getType());
        imgOut.setRGB(newStartX, newStartY, newWidth, newHeight, newPix, newOffset, newScansize);
        ImageIO.write(imgOut, "png", new File(newFilePath));
    }

}