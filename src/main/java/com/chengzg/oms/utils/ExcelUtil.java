package com.chengzg.oms.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csvreader.CsvReader;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.io.File.separator;

public class ExcelUtil {
	private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);


	/**
	 * 导入
	 *
	 * @param file csv文件(路径+文件)
	 * @return
	 */
	public static JSONArray importCsv(File file){
		JSONArray resultList = new JSONArray();
		BufferedReader br=null;

		try {
			List<String[]> dataList=new ArrayList<String[]>();
			resultList = importCsv(file.getPath());
		}catch (Exception e) {
		}finally{
			if(br!=null){
				try {
					br.close();
					br=null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return resultList;
	}


	public static JSONArray importCsv(String filePath) throws Exception {
		CsvReader reader = null;
		List<String[]> dataList = new ArrayList<String[]>();
		JSONArray result = new JSONArray();

		try {
			reader = new CsvReader(filePath,',',Charset.forName("GBK"));    //一般用这编码读就可以了

			// 读取表头
			reader.readHeaders();
			String[] headers = reader.getHeaders();
			// 逐条读取记录，直至读完
			while (reader.readRecord()) {
				JSONObject dataObj = new JSONObject();
				for (String header : headers) {
					String value = reader.get(header);
					dataObj.put(header, value);
				}
				result.add(dataObj);
			}
		} catch (Exception e) {
			System.out.println("读取CSV出错..." + e);
			throw e;
		} finally {
			if (null != reader) {
				reader.close();
			}
		}

		return result;
	}


	public static void main(String[] args) throws Exception {

	}

}
