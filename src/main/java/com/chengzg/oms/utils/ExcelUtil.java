package com.chengzg.oms.utils;

import com.alibaba.fastjson.JSON;
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

	public static JSONArray importCsv(File file,String charset){
		JSONArray resultList = new JSONArray();
		BufferedReader br=null;

		try {
			List<String[]> dataList=new ArrayList<String[]>();
			resultList = importCsv(file.getPath(), charset);
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


	public static JSONObject importExcel(
			String path, int sheetNum) {
		InputStream is = null;
		Workbook wb = null;
		// 返回数据
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, List<Map<String, Object>>> sheetMap = new HashMap<String, List<Map<String, Object>>>();
		try {
			// 获取输入流
			is = new FileInputStream(path);

			// 创建页签对象
			wb = Workbook.getWorkbook(is);
			// 获取页签中第一工作簿
			Sheet st = null;

			for (int num = 0; num < sheetNum; num++) {
				st = wb.getSheet(num);

				list = readSheet(st);
				sheetMap.put("sheet" + num, list);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("解析文件出错，请查看日志！");
		} finally {
			if (wb != null) {
				wb.close();// 关闭工作薄
				wb = null;
			}
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
				log.info("解析文件出错，请查看日志！");
			}
		}

		return JSON.parseObject(JSON.toJSONString(sheetMap));
	}


	public static List<Map<String, Object>> readSheet(Sheet st)
			throws Exception {
		// 返回数据
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 列数
		int columns = st.getColumns();
		// 行数
		int rows = st.getRows();

		// 封装数据
		for (int i = 1; i < rows; i++) {

			Map<String, Object> map = new HashMap<String, Object>();

			for (int j = 0; j < columns; j++) {

				// 取出单元格
				Cell fieldName = st.getCell(j, 0);
				// 列名
				String fieldNameValue = fieldName.getContents();
				// 判断是否字段名不为空
				if (StringUtils.isNotBlank(fieldNameValue)) {

					fieldNameValue = replaceBlank(fieldNameValue);
					// 得到工作表的单元格,即A1
					Cell cell = st.getCell(j, i);
					// getContents()将Cell中的字符转为字符串
					Object value = cell.getContents();
					// valueMap.put(fieldNameValue,
					// value);//key(字段ID)--value对
					if (value == null || StringUtils.isBlank(value.toString())) {
						continue;
					}
					log.info("fieldNameValue=" + fieldNameValue + " 对应的value="
							+ value);

					map.put(fieldNameValue, value);

				}
			}
			list.add(map);// 把第一条记录加入到list中去
		}
		return list;

	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}


	public static JSONArray importCsv(String filePath, String charset) throws Exception {
		CsvReader reader = null;
		List<String[]> dataList = new ArrayList<String[]>();
		JSONArray result = new JSONArray();

		try {
			reader = new CsvReader(filePath,',',Charset.forName(charset));    //一般用这编码读就可以了

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
