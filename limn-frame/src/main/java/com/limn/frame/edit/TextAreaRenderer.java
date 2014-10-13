package com.limn.frame.edit;

import java.awt.Component;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class TextAreaRenderer extends JTextArea implements TableCellRenderer{
	   private final DefaultTableCellRenderer adaptee = new DefaultTableCellRenderer();
	   
	   /** map from table to map of rows to map of column heights */
	   private final Map cellSizes = new HashMap();

	   public TextAreaRenderer()
	   {
	      setLineWrap(true);
	      setWrapStyleWord(true);
	   }

	   public Component getTableCellRendererComponent(JTable table, Object obj, 
			   boolean isSelected, boolean hasFocus, int row, int column){
	      adaptee.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
	      setForeground(adaptee.getForeground());
	      setBackground(adaptee.getBackground());
	      setBorder(adaptee.getBorder());
	      setFont(adaptee.getFont());
	      setText(adaptee.getText());

	      TableColumnModel columnModel = table.getColumnModel();
	      setSize(columnModel.getColumn(column).getWidth(), 100000);
	      int height_wanted = (int)getPreferredSize().getHeight();
	      height_wanted = findTotalMaximumRowSize(table, row);
	      if (height_wanted != table.getRowHeight(row))
	      {
	         table.setRowHeight(row, height_wanted);
	      }
	      return this;
	   }

	   /**
	    * Look through all columns and get the renderer. If it is also a TextAreaRenderer, we look at the maximum
	    * height in its hash table for this row.
	    */
	   private int findTotalMaximumRowSize(JTable table, int row)
	   {
	      int maximum_height = 0;
	      Enumeration columns = table.getColumnModel().getColumns();
	      while (columns.hasMoreElements())
	      {
	         TableColumn tc = (TableColumn)columns.nextElement();
	         TableCellRenderer cellRenderer = tc.getCellRenderer();
	         if (cellRenderer instanceof TextAreaRenderer)
	         {
	            TextAreaRenderer tar = (TextAreaRenderer)cellRenderer;
	            maximum_height = Math.max(maximum_height, tar.findMaximumRowSize(table, row));
	         }
	      }
	      return maximum_height;
	   }

	   private int findMaximumRowSize(JTable table, int row)
	   {
	      Map rows = (Map)cellSizes.get(table);
	      if (rows == null)
	         return 0;
	      Map rowheights = (Map)rows.get(new Integer(row));
	      if (rowheights == null)
	         return 0;
	      int maximum_height = 0;
	      for (Iterator it = rowheights.entrySet().iterator();  it.hasNext(); )
	      {
	         Map.Entry entry = (Map.Entry)it.next();
	         int cellHeight = ((Integer)entry.getValue()).intValue();
	         maximum_height = Math.max(maximum_height, cellHeight);
	      }
	      return maximum_height;
	   }
	}