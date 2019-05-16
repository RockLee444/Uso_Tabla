/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Luis Matuz
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import static javax.swing.JTable.AUTO_RESIZE_OFF;
import javax.swing.event.*;
import javax.swing.table.*;
//JTable Fila horizontal, columna vertical

public class TablaInteractiva extends JPanel implements ActionListener {
    
    private JFrame ventana;
    private JTable tabla;
    private DefaultTableModel model;
    private JButton botonAgregar;
    private JButton botonEditar;
    private JButton botonEliminar;
    private JLabel mensaje;
    private JLabel mensaje2;
    private JTextField caja;
    private JTextField caja2;
  
    private int id;
    private int selectedRow;
    private int selectedId;
    int contador;
    
    
    public TablaInteractiva(){
    super();
       id=4;
       contador=0;
       selectedRow=0;
       selectedId=0;
      String data[][] = {
          {"1","Tacos","$ 12.0"},
          {"2","Tamales","$ 10.0"},
          {"3","Tortas","$ 35.0"},
          {"4","Cochito","$ 45.0"},
      };
      
      String label[] = {"ID","NOMBRE","PRECIO"};
      
      model = new DefaultTableModel(data,label);
      tabla = new JTable(model);
      tabla.setCellSelectionEnabled(true);
      tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      
      //LO SIGUIENTE ES PARA IDENTIFICAR EL ELEMENTO ESCOGIDO POR EL USUARIO
      ListSelectionModel select = tabla.getSelectionModel();
      select.addListSelectionListener(new ListSelectionListener(){
         public void valueChanged(ListSelectionEvent e){
            String Data = null;
            int row = tabla.getSelectedRow();
            int column = tabla.getSelectedColumn();

           Data = (String) tabla.getValueAt(row,column);
           caja.setText((String)tabla.getValueAt(row,1));
           
           String aux[]= ((String)tabla.getValueAt(row,2)).split(" ");
           selectedRow=row;
           selectedId= java.lang.Integer.parseInt((String)tabla.getValueAt(row,0));
           caja2.setText(aux[1]);
           botonAgregar.setEnabled(false);
           botonEditar.setEnabled(true);
           botonEliminar.setEnabled(true);
         }
      });
      //Configuracion de la ventana
      TableColumnModel columnas = tabla.getColumnModel();
      
      for(int i=0;i<3;i++){
       columnas.getColumn(i).setPreferredWidth(100);
      }
      
      
      tabla.setAutoResizeMode(AUTO_RESIZE_OFF);
      tabla.setRowHeight(50);
      JScrollPane scroll = new JScrollPane(tabla);
      ventana = new JFrame();
      crearComponentes();
      ventana.add(scroll);
      ventana.pack();
      ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      ventana.setPreferredSize(new Dimension(700,700));
      ventana.pack();
      ventana.setVisible(true);
      
    }

    
    private void crearComponentes(){
        
        mensaje = new JLabel(); //Se crea el objeto JLabel
        mensaje.setText("Nombre"); // Se crea el texto a mostrar
        mensaje.setBounds(320,0, 300, 30); // Posición X e Y del texto y su tamaño.
        mensaje.setForeground(Color.BLACK); //Color del texto
        ventana.add(mensaje); //Añadir el texto a la ventana
        
        mensaje2 = new JLabel(); //Se crea el objeto JLabel
        mensaje2.setText("Precio"); // Se crea el texto a mostrar
        mensaje2.setBounds(440,0,300, 30); // Posición X e Y del texto y su tamaño.
        mensaje2.setForeground(Color.BLACK); //Color del texto
        ventana.add(mensaje2); //Añadir el texto a la ventana

        caja = new JTextField();
        caja.setBounds(320,30,100,30);
        ventana.add(caja);
        
        caja2 = new JTextField();
        caja2.setBounds(440,30,100,30);
        ventana.add(caja2);
             
        botonAgregar = new JButton();
        botonAgregar.setText("Agregar");
        botonAgregar.setBounds(320,80, 100, 30);
        botonAgregar.addActionListener(this);
        ventana.add(botonAgregar);
        
        botonEditar = new JButton();
        botonEditar.setText("Editar");
        botonEditar.setBounds(440,80, 100, 30);
        botonEditar.addActionListener(this);
        botonEditar.setEnabled(false);
        ventana.add(botonEditar);
        
        botonEliminar = new JButton();
        botonEliminar.setText("Eliminar");
        botonEliminar.setBounds(550,80, 100, 30);
        botonEliminar.addActionListener(this);
        botonEliminar.setEnabled(false);
        ventana.add(botonEliminar);
    }
  
    
    @Override 
    public void actionPerformed(ActionEvent e){
        //try{
                JButton aux =(JButton) e.getSource();
                String botonPulsado = aux.getText();
                switch (botonPulsado){
                    case "Agregar":
                        if(contador==0){
                            String nombre = caja.getText();
                            double precioTotal= java.lang.Double.parseDouble(caja2.getText());
                            String precio = "$ " + precioTotal;
                            addRow(nombre,precio);    
                            ventana.repaint();
                        }
                    break;
                    
                    case "Editar":
                        if(contador==0){
                            String nombre = caja.getText();
                            double precioNuevo= java.lang.Double.parseDouble(caja2.getText());
                            String precio = "$ " + precioNuevo;
                            addDataInSpecificRow(selectedRow,selectedId,nombre,precio);
                            restartWindow();
                            deleteRow(selectedRow);
                            ventana.repaint();
                            ventana.revalidate();
                            contador=2;
                        }
                        
                    break;
                    
                    case "Eliminar":                   
                        if(selectedRow>=0 && selectedRow<tabla.getRowCount()){
                            contador=2;
                            restartWindow();
                            deleteRow(selectedRow);
                        }
                        
                    break;
                }                
                
                if(contador==2){
                    restartWindow();
                }
        //}
        /*
        catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Por favor ingrese un valor válido.", "ERROR", 1);
        }
*/

    }
    
    public void restartWindow(){
        caja.setText("");
        contador=0;
        caja2.setText("");
        botonAgregar.setEnabled(true);
        botonEditar.setEnabled(false);
        botonEliminar.setEnabled(false);
        ventana.repaint();
        ventana.revalidate();
    }
    
    public void addRow(String str1, String str2){
        id++;
        model.addRow(new Object[]{""+id,str1,str2});
    }
    
    public void addDataInSpecificRow(int row,int selectedId,String str1, String str2){
        model.insertRow(row, new Object[] {selectedId,str1,str2});
    }
    
    public void deleteRow(int fila){
        if(model.getRowCount()>0){
           model.removeRow(fila);
           restartWindow();
        }
        else {
            JOptionPane.showMessageDialog(tabla, "Tabla vacía.","ERROR", 1);
        }
       
    }
    
    public static void main(String[] args){
       TablaInteractiva x = new TablaInteractiva(); 
    }
}
