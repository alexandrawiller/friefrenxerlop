package com.bayside.app.util;

import java.io.ByteArrayInputStream;  
import java.io.IOException;  
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
import java.util.Map.Entry;  
import org.apache.poi.POIXMLDocument;  
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;  
import org.apache.poi.xwpf.usermodel.XWPFRun;  
import org.apache.poi.xwpf.usermodel.XWPFTable;  
import org.apache.poi.xwpf.usermodel.XWPFTableCell;  
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTInd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;

import com.bayside.app.util.CustomXWPFDocument;  
  
/** 
 * 适用于word 2007 
 * poi 版本 3.7 
 */  
public class WordUtil {  
  
    /** 
     * 根据指定的参数值、模板，生成 word 文档 
     * @param param 需要替换的变量 
     * @param template 模板 
     */  
    public static CustomXWPFDocument generateWord(Map<String, Object> param, String template) {  
        CustomXWPFDocument doc = null;  
        try {  
            OPCPackage pack = POIXMLDocument.openPackage(template);  
            doc = new CustomXWPFDocument(pack);  
            if (param != null && param.size() > 0) {  
                //处理段落  
                List<XWPFParagraph> paragraphList = doc.getParagraphs();  
                processParagraphs(paragraphList, param, doc);  
                //处理表格  
               /* Iterator<XWPFTable> it = doc.getTablesIterator();  
                while (it.hasNext()) {  
                    XWPFTable table = it.next();  
                    List<XWPFTableRow> rows = table.getRows();  
                    for (XWPFTableRow row : rows) {  
                        List<XWPFTableCell> cells = row.getTableCells();  
                        for (XWPFTableCell cell : cells) {  
                            List<XWPFParagraph> paragraphListTable =  cell.getParagraphs();  
                            processParagraphs(paragraphListTable, param, doc);  
                        }  
                    }  
                }  */
            }  
        } catch (Exception e) { 
        	//System.out.println(e);
            e.printStackTrace();  
        }  
        return doc;  
    }  
    /** 
     * 处理段落 
     * @param paragraphList 
     */  
    public static void processParagraphs(List<XWPFParagraph> paragraphList,Map<String, Object> param,CustomXWPFDocument doc){  
        if(paragraphList != null && paragraphList.size() > 0){  
            for(XWPFParagraph paragraph:paragraphList){  
                List<XWPFRun> runs = paragraph.getRuns(); 
                for (int i = 0; i < runs.size(); i++) {
                	XWPFRun run = runs.get(i);
                	
                    String text = run.toString();
                    System.out.println(text);
                    if(text != null){  
                        boolean isSetText = false;  
                        //for (Entry<String, Object> entry : param.entrySet()) {  
                           // String key = entry.getKey();  
                           // if(text.indexOf(key) != -1){  
                        if(param.get(text)!=null){
                                isSetText = true;  
                               // Object value = entry.getValue();  
                                Object value = param.get(text);
                                if (value instanceof String) {//文本替换  
                                    text = text.replace(text, value.toString());
                                } else if (value instanceof Map) {//图片替换  
                                    text = text.replace(text, "");  
                                    Map pic = (Map)value;  
                                    int width = Integer.parseInt(pic.get("width").toString());  
                                    int height = Integer.parseInt(pic.get("height").toString());  
                                    int picType = getPictureType(pic.get("type").toString());  
                                    byte[] byteArray = (byte[]) pic.get("content");  
                                    ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);  
                                    try {  
                                        String ind = doc.addPictureData(byteArray,picType);  
                                        doc.createPicture(paragraph, doc.getAllPictures().size()-1, width, height, "");
                                    } catch (Exception e) {  
                                        e.printStackTrace();  
                                    }  
                                   
                                }  
                            }  
                       // }  
                        if(isSetText){  
                            run.setText(text,0);  
                        }  
                    }  
                }  
                
            }  
        }  
    }  
    public static void insertValueToTable(XWPFDocument doc,
    	      Map<String,Object> map,boolean isDelTmpRow) throws Exception {
    	    Iterator<XWPFTable> iterator = doc.getTablesIterator();
    	    List<List<String>> resultList = new ArrayList<List<String>>();
    	   
    	    while (iterator.hasNext()) {
    	    	 XWPFTable table = null;
    	    	 List<XWPFTableRow> rows=null;
    	    	 List<XWPFTableCell> cells=null;
    	    	 List<XWPFTableCell> tmpCells=null;//模版列
    	    	 XWPFTableRow tmpRow=null;//匹配用
    	    	 XWPFTableCell tmpCell=null;//匹配用
    	    	 //boolean flag=false;//是否找到表格
    	      table = iterator.next();
    	      rows = table.getRows();
    	      String text = rows.get(1).getCell(0).getParagraphs().get(0).getText();
    	      System.out.println(text);
    	      Object obj = map.get(text);
    	      if(obj!=null){
    	        tmpRow=rows.get(1);
    	        cells =tmpRow.getTableCells();
    	          //flag=true;
    	          resultList = (List<List<String>>)obj;
    	          tmpCells=tmpRow.getTableCells();
    	    	    for(int i=0,len=resultList.size();i<len;i++){
    	    	      XWPFTableRow row=table.createRow();
    	    	      row.setHeight(tmpRow.getHeight());
    	    	      List<String> list=resultList.get(i);
    	    	      cells=row.getTableCells();
    	    	      //插入的行会填充与表格第一行相同的列数
    	    	      for(int k=0,klen=cells.size();k<klen;k++){
    	    	        tmpCell=tmpCells.get(k);
    	    	        XWPFTableCell cell=cells.get(k);
    	    	          setCellText(tmpCell, cell, list.get(k));
    	    	      }
    	    	      //继续写剩余的列
    	    	      for(int j=cells.size(),jlen=list.size();j<jlen;j++){
    	    	        tmpCell=tmpCells.get(j);
    	    	        XWPFTableCell cell=row.addNewTableCell();
    	    	          setCellText(tmpCell, cell, list.get(j));
    	    	      }
    	    	    }
    	    	    //删除模版行
    	    	    if(isDelTmpRow){
    	    	      table.removeRow(1);
    	    	    }
    	      }
    	    }
    	  }
    	  
    	  public static void setCellText(XWPFTableCell tmpCell,XWPFTableCell cell,String text) throws Exception{
    	    CTTc cttc2 = tmpCell.getCTTc();
    	    CTTcPr ctPr2=cttc2.getTcPr();
    	    
    	    CTTc cttc = cell.getCTTc();
    	    CTTcPr ctPr = cttc.addNewTcPr();
    	    cell.setColor(tmpCell.getColor());
    	    cell.setVerticalAlignment(tmpCell.getVerticalAlignment());
    	    if(ctPr2.getTcW()!=null){
    	      ctPr.addNewTcW().setW(ctPr2.getTcW().getW());
    	    }
    	    if(ctPr2.getVAlign()!=null){
    	      ctPr.addNewVAlign().setVal(ctPr2.getVAlign().getVal());
    	    }
    	    if(cttc2.getPList().size()>0){
    	      CTP ctp=cttc2.getPList().get(0);
    	      if(ctp.getPPr()!=null){
    	        if(ctp.getPPr().getJc()!=null){
    	          cttc.getPList().get(0).addNewPPr().addNewJc().setVal(ctp.getPPr().getJc().getVal());
    	        }
    	      }
    	    }
    	    
    	    if(ctPr2.getTcBorders()!=null){
    	      ctPr.setTcBorders(ctPr2.getTcBorders());
    	    }
    	    
    	    XWPFParagraph tmpP=tmpCell.getParagraphs().get(0);
    	    XWPFParagraph cellP=cell.getParagraphs().get(0);
    	    XWPFRun tmpR =null;
    	    if(tmpP.getRuns()!=null&&tmpP.getRuns().size()>0){
    	      tmpR=tmpP.getRuns().get(0);
    	    }
    	    XWPFRun cellR = cellP.createRun();
    	    cellR.setText(text);
    	    //复制字体信息
    	    if(tmpR!=null){
    	      cellR.setBold(tmpR.isBold());
    	      cellR.setItalic(tmpR.isItalic());
    	      cellR.setStrike(tmpR.isStrike());
    	      cellR.setUnderline(tmpR.getUnderline());
    	        cellR.setColor(tmpR.getColor());
    	      cellR.setTextPosition(tmpR.getTextPosition());
    	      if(tmpR.getFontSize()!=-1){
    	        cellR.setFontSize(tmpR.getFontSize());
    	      }
    	      if(tmpR.getFontFamily()!=null){
    	        cellR.setFontFamily(tmpR.getFontFamily());
    	      }
    	      if(tmpR.getCTR()!=null){
    	        if(tmpR.getCTR().isSetRPr()){
    	          CTRPr tmpRPr =tmpR.getCTR().getRPr();
    	          if(tmpRPr.isSetRFonts()){
    	            CTFonts tmpFonts=tmpRPr.getRFonts();
    	            CTRPr cellRPr=cellR.getCTR().isSetRPr() ? cellR.getCTR().getRPr() : cellR.getCTR().addNewRPr();
    	            CTFonts cellFonts = cellRPr.isSetRFonts() ? cellRPr.getRFonts() : cellRPr.addNewRFonts();
    	            cellFonts.setAscii(tmpFonts.getAscii());
    	            cellFonts.setAsciiTheme(tmpFonts.getAsciiTheme());
    	            cellFonts.setCs(tmpFonts.getCs());
    	            cellFonts.setCstheme(tmpFonts.getCstheme());
    	            cellFonts.setEastAsia(tmpFonts.getEastAsia());
    	            cellFonts.setEastAsiaTheme(tmpFonts.getEastAsiaTheme());
    	            cellFonts.setHAnsi(tmpFonts.getHAnsi());
    	            cellFonts.setHAnsiTheme(tmpFonts.getHAnsiTheme());
    	          }
    	        }
    	      }
    	    }
    	    //复制段落信息
    	    cellP.setAlignment(tmpP.getAlignment());
    	    cellP.setVerticalAlignment(tmpP.getVerticalAlignment());
    	    cellP.setBorderBetween(tmpP.getBorderBetween());
    	    cellP.setBorderBottom(tmpP.getBorderBottom());
    	    cellP.setBorderLeft(tmpP.getBorderLeft());
    	    cellP.setBorderRight(tmpP.getBorderRight());
    	    cellP.setBorderTop(tmpP.getBorderTop());
    	    cellP.setPageBreak(tmpP.isPageBreak());
    	    if(tmpP.getCTP()!=null){
    	      if(tmpP.getCTP().getPPr()!=null){
    	        CTPPr tmpPPr = tmpP.getCTP().getPPr();
    	        CTPPr cellPPr = cellP.getCTP().getPPr() != null ? cellP.getCTP().getPPr() : cellP.getCTP().addNewPPr();
    	        //复制段落间距信息
    	        CTSpacing tmpSpacing =tmpPPr.getSpacing();
    	        if(tmpSpacing!=null){
    	          CTSpacing cellSpacing= cellPPr.getSpacing()!=null?cellPPr.getSpacing():cellPPr.addNewSpacing();
    	          if(tmpSpacing.getAfter()!=null){
    	            cellSpacing.setAfter(tmpSpacing.getAfter());
    	          }
    	          if(tmpSpacing.getAfterAutospacing()!=null){
    	            cellSpacing.setAfterAutospacing(tmpSpacing.getAfterAutospacing());
    	          }
    	          if(tmpSpacing.getAfterLines()!=null){
    	            cellSpacing.setAfterLines(tmpSpacing.getAfterLines());
    	          }
    	          if(tmpSpacing.getBefore()!=null){
    	            cellSpacing.setBefore(tmpSpacing.getBefore());
    	          }
    	          if(tmpSpacing.getBeforeAutospacing()!=null){
    	            cellSpacing.setBeforeAutospacing(tmpSpacing.getBeforeAutospacing());
    	          }
    	          if(tmpSpacing.getBeforeLines()!=null){
    	            cellSpacing.setBeforeLines(tmpSpacing.getBeforeLines());
    	          }
    	          if(tmpSpacing.getLine()!=null){
    	            cellSpacing.setLine(tmpSpacing.getLine());
    	          }
    	          if(tmpSpacing.getLineRule()!=null){
    	            cellSpacing.setLineRule(tmpSpacing.getLineRule());
    	          }
    	        }
    	        //复制段落缩进信息
    	        CTInd tmpInd=tmpPPr.getInd();
    	        if(tmpInd!=null){
    	          CTInd cellInd=cellPPr.getInd()!=null?cellPPr.getInd():cellPPr.addNewInd();
    	          if(tmpInd.getFirstLine()!=null){
    	            cellInd.setFirstLine(tmpInd.getFirstLine());
    	          }
    	          if(tmpInd.getFirstLineChars()!=null){
    	            cellInd.setFirstLineChars(tmpInd.getFirstLineChars());
    	          }
    	          if(tmpInd.getHanging()!=null){
    	            cellInd.setHanging(tmpInd.getHanging());
    	          }
    	          if(tmpInd.getHangingChars()!=null){
    	            cellInd.setHangingChars(tmpInd.getHangingChars());
    	          }
    	          if(tmpInd.getLeft()!=null){
    	            cellInd.setLeft(tmpInd.getLeft());
    	          }
    	          if(tmpInd.getLeftChars()!=null){
    	            cellInd.setLeftChars(tmpInd.getLeftChars());
    	          }
    	          if(tmpInd.getRight()!=null){
    	            cellInd.setRight(tmpInd.getRight());
    	          }
    	          if(tmpInd.getRightChars()!=null){
    	            cellInd.setRightChars(tmpInd.getRightChars());
    	          }
    	        }
    	      }
    	    }
    	  }
    /** 
     * 根据图片类型，取得对应的图片类型代码 
     * @param picType 
     * @return int 
     */  
    private static int getPictureType(String picType){  
        int res = CustomXWPFDocument.PICTURE_TYPE_PICT;  
        if(picType != null){  
            if(picType.equalsIgnoreCase("png")){  
                res = CustomXWPFDocument.PICTURE_TYPE_PNG;  
            }else if(picType.equalsIgnoreCase("dib")){  
                res = CustomXWPFDocument.PICTURE_TYPE_DIB;  
            }else if(picType.equalsIgnoreCase("emf")){  
                res = CustomXWPFDocument.PICTURE_TYPE_EMF;  
            }else if(picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")){  
                res = CustomXWPFDocument.PICTURE_TYPE_JPEG;  
            }else if(picType.equalsIgnoreCase("wmf")){  
                res = CustomXWPFDocument.PICTURE_TYPE_WMF;  
            }  
        }  
        return res;  
    }  
    /** 
     * 将输入流中的数据写入字节数组 
     * @param in 
     * @return 
     */  
    public static byte[] inputStream2ByteArray(InputStream in,boolean isClose){  
        byte[] byteArray = null;  
        try {  
            int total = in.available();  
            byteArray = new byte[total];  
            in.read(byteArray);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            if(isClose){  
                try {  
                    in.close();  
                } catch (Exception e2) {  
                    System.out.println("关闭流失败");  
                }  
            }  
        }  
        return byteArray;  
    }  
}  