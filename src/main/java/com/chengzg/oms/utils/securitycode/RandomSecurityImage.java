package com.chengzg.oms.utils.securitycode;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class RandomSecurityImage {
	private static Logger logger = Logger.getLogger(RandomSecurityImage.class);
	private static Random random = new Random();
	 
    private static Font font = new Font("Fixedsys", Font.CENTER_BASELINE, 18);
 
    /**
     * 根据指定的字符和大小生成随机验证码图片
     * @param code 需要绘制到图片上的字符数组
     * @param width 图片宽度
     * @param height 图片高度
     * @param line 干扰线数量
     * @return 图片的输入流
     */
    public static BufferedImage getImage(String code, int line) {
    	if(StringUtils.isBlank(code)) {
    		code = "";
    	}
    	char[] chars = code.toCharArray();
    	//验证码长度
        int codeLength=chars.length;
        //字体大小
        int fSize = 15;
        int fWidth = fSize + 1;
        //图片宽度
        int width = codeLength * fWidth + 6 ;
        //图片高度
        int height = fSize * 2 + 1;
        
    	
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
        g.setColor(getRandColor(110, 133));
        // 绘制干扰线
        for (int i = 0; i < line; i++) {
            drowLine(g, width, height);
        }
        // 绘制随机字符
        for (int i = 0; i < chars.length; i++) {
            drowChar(g, width / chars.length * i, random.nextInt(height / 3)
                    + height / 3, chars[i]);
        }
        g.dispose();
 
        return image;
    }
 
    /**
     * 将BufferedImage转换成ByteArrayInputStream
     * 
     * @param image
     *            图片
     * @return ByteArrayInputStream 流
     */
    private static ByteArrayInputStream convertImageToStream(BufferedImage image) {
        ByteArrayInputStream inputStream = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "JPEG", bos);
            byte[] bts = bos.toByteArray();
            inputStream = new ByteArrayInputStream(bts);
        } catch (IOException e1) {
        }
        return inputStream;
    }
 
    /*
     * 获得颜色
     */
    private static Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }
 
    /*
     * 绘制字符
     */
    private static char drowChar(Graphics g, int x, int y, char code) {
        g.setFont(font);
        g.setColor(getRandColor(10, 200));
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(code + "", x, y);
        return code;
    }
 
    /*
     * 绘制干扰线
     */
    private static void drowLine(Graphics g, int width, int height) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(width / 2);
        int yl = random.nextInt(height / 2);
        g.drawLine(x, y, x + xl, y + yl);
    }
    
    
    public static boolean saveBufferedImage(BufferedImage bufferedImage, String path, String imageName) {
    	try {
			Image big = bufferedImage.getScaledInstance(256, 256,Image.SCALE_DEFAULT);
			BufferedImage inputbig = new BufferedImage(256, 256,BufferedImage.TYPE_INT_BGR);
			inputbig.getGraphics().drawImage(bufferedImage, 0, 0, 256, 256, null); //画图

			File pathfile =new File(path);            //此目录保存缩小后的关键图
			if(pathfile.exists()){
				logger.info("saveBySecurityImage [" + path + "]存在");
			}else{
				//如果要创建的多级目录不存在才需要创建。
				pathfile.mkdirs();
			}
			ImageIO.write(inputbig, "jpg", new File(path + "/"+imageName));   //将其保存在C:/imageSort/targetPIC/下
			return true;
		} catch (Exception e) {
			logger.error("saveBySecurityImage 异常：" + e.getMessage());
		}
    	return false;
    }
}
