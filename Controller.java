package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.pdfbox.*;
import org.apache.pdfbox.pdmodel.PDDocument;

import org.apache.pdfbox.pdmodel.PDPage;
import org.xhtmlrenderer.pdf.ITextRenderer;
import com.lowagie.text.DocumentException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import javax.swing.filechooser.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;


public class Controller {
    @FXML Label l = new Label();
    @FXML Label lp = new Label();
    @FXML Label printername = new Label();
    @FXML public ListView listview = new ListView();
    @FXML public Button openButton;
    @FXML public Button printbutton;
    @FXML public MenuButton select_file;
    String extension;
    String pdf = "C:/Users/user/Desktop/VSC/Test.pdf";
    boolean ob;
    boolean sf;

    public void getPrintername() {
        PrintService service1 = PrintServiceLookup.lookupDefaultPrintService();
        printername.setText(service1.getName());

    }

    public void getDefaultname() {

        printername.setText("Move mouse here to reveal Default Printer");

    }

    public void initialize(String name) {
       //ObservableList<String> list = FXCollections.observableArrayList(name);
        listview.getItems().addAll(name);
        //listview.getItems().addAll(list);
        Alert printAlert = new Alert(AlertType.INFORMATION);
        printAlert.setTitle("Print Status");
        printAlert.setContentText("Printing in Progress...");
        printAlert.show();

        ButtonType buttonOne = new ButtonType("Ok");
       ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
      printAlert.getButtonTypes().setAll(buttonOne, buttonCancel);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        printAlert.close();
                    }
                });
            }
        });
        thread.start();

        /*Alert printAlert = new Alert(AlertType.INFORMATION);
        printAlert.setTitle("Print Status");
        printAlert.setContentText("Printing in Progress...");
        printAlert.show();
        printAlert.close(); */

    }



    public void selectFile(){
        JFileChooser jc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jc.setMultiSelectionEnabled(true);
        jc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        // invoke the showsOpenDialog function to show the open dialog
        int r = jc.showOpenDialog(null);

        if (r == JFileChooser.APPROVE_OPTION) {

            // set the label to the path of the selected directory
            l.setText(jc.getSelectedFile().getAbsolutePath());
        }
        // if the user cancelled the operation
        else {
            l.setText("the user cancelled the operation");

        }


    }


    public void folderButton() {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // invoke the showsOpenDialog function to show the open dialog
                int r = j.showOpenDialog(null);

                if (r == JFileChooser.APPROVE_OPTION) {

                    // set the label to the path of the selected directory
                    l.setText(j.getSelectedFile().getAbsolutePath());
                }
                // if the user cancelled the operation
                else {
                    l.setText("the user cancelled the operation");

                }

            }



        public void printButton() throws Exception {
            lp.setText("select folder");

            ob = openButton.isPressed();
            sf = select_file.isPressed();
            
            if ((ob = true) || (sf = true) ) {
                String path;
                path = l.getText();
                try {
                    sortAll(path);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

    }

        public void clearButton() {

            listview.getItems().clear();
    }

        public void aboutButton(){
            Alert about = new Alert(AlertType.INFORMATION);
            about.setTitle("About Program");
            about.setHeaderText("About");
            about.setContentText("This Program is specifically designed for the purpose of helping in the printing of NABTEB Photo-Album." +
                    " Its major function is to speed up the printing process of the required photo-album of large quantities in lesser time, and less effort and even" +
                    " reduce the number of staff it may take to do such work." + "\n" +
                            "*******************************************************************"+ "\n" +
                    "Created By AUSTIN OGBEIDE for the National Business and Technical Examination Board (NABTEB)" +
                    "\n" + "*******************************************************************"+ "\n" +
                    "Thanks to my fellow IT guys:" +"\n" + "JUDE ONOHWOSAFE, ANTHONY EKOH,GODSWILL OSAKWE,WISDOM ADAMS and OSADEBAWME OKOYO." +"\n" +
                    "Staffs and Management of ICT department.");
           about.showAndWait();
        }

        public void helpButton(){

        Alert help = new Alert(AlertType.INFORMATION);
        help.setTitle("Help");
        help.setHeaderText("How To Use Program");
        help.setContentText("CLICK on Open Folder and select the folder with the required photo-album that requires printing," +
                "then CLICK on Print button to start the printing process." +
                "\n" + "If the User by any means made an error during printing and wants to re-print a certain file, simply" +
                " CLICK on 'file' and then 'select file' and select the folder that the file is located." +
                "\n" +
                "*******************************************************************" +
                "\n" +
                "*******************************************************************" +
                "\n" +
                "NOTE: The printing of documents is automated and cannot be STOPPED as soon as the process begins.");



        help.showAndWait();


        }

       /* public void generatePdf(File htmFile, String pdfdoc) throws DocumentException, IOException{
            try{
                URL url = new File(htmFile.toString()).toURI().toURL();
                System.out.println("URL: " +url);
                OutputStream out = new FileOutputStream(pdfdoc);

                //Flying Saucer part

                ITextRenderer renderer = new ITextRenderer();
               
               renderer.setDocument(url);
                renderer.layout();
                renderer.createPDF(out);

               out.close();
            }
            catch(DocumentException | IOException e){
                e.printStackTrace();
            }
        }*/

    public static void print(String value) {
        System.out.println(value);
    }
    





    public void sortAll(String dirName) throws Exception {

        File dir = new File(dirName);
        File[] fileArray = dir.listFiles();
        

        //sort through array
        Arrays.sort(fileArray);
        for (File file : fileArray)

            //checks and prints name of files in folder

            if (file.isFile() && !file.isHidden()) {
                extension = file.toString().substring(file.toString().indexOf("."));
                //print("File extension is " + extension);

                if (file.getName().toString().endsWith(".JPG") && !file.isHidden()) {

                   // print("Name of JPG " + file.getName());

                }
                if ((file.getName().endsWith(".HTM") || file.getName().endsWith(".html")) && !file.isHidden() && fileArray.length == 1) {
                   // print("Only 1 HTM file here" +file.getName());
                    break;
                }
                if ((file.getName().endsWith(".HTM") || file.getName().endsWith(".html")) && !file.isHidden() && fileArray.length != 2) {
                   // print("Printing only of html extension " + file.getName());
                    initialize(file.getName());
                   // print("File Array length" +fileArray.length);




                    FileInputStream fis = new FileInputStream(file);
                    PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
                   // DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
                   DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
                   //PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
                    PrintService service = PrintServiceLookup.lookupDefaultPrintService();




                  /*  if (service != null) {
                       PrinterJob job = PrinterJob.getPrinterJob();
                        PageFormat pgf = new PageFormat();
                        pgf.setOrientation(PageFormat.PORTRAIT);
                        Paper paper = new Paper();
                        paper.setSize(595, 842);
                        paper.setImageableArea(0, 0, 595, 482);
                        pgf.setPaper(paper);
                        PDDocument doc = PDDocument.load(file);
                    
                       job.setPrintable(new Printable(){
                       
                          @Override
                           public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                               
                               if (pageIndex > 0){
                                   return NO_SUCH_PAGE;
                               }
                               Graphics2D g2d = (Graphics2D)graphics;
                               g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                             
                               
                               return Printable.PAGE_EXISTS;
                           }
                       }, pgf); */
                    
                       
                       
                       
                        
            
                      // DocPrintJob job = service.createPrintJob();
                      // DocAttributeSet das = new HashDocAttributeSet();
                    
                       // Doc doc = new SimpleDoc(fis, flavor, das);
                       //job.print(doc, pras);


                        // JEditorPane pane = new JEditorPane();
                         // pane.setEditable(false);
                        //URL urlToPage = new File(file.toString()).toURI().toURL();
                   // pane.setPage(urlToPage);
                          //pane.print(null, null, false, service, pras, false);


                      //    JEditorPane pane = new JEditorPane();
     // {
       //  @Override
       //  public boolean getScrollableTracksViewportWidth(){
      //      return true;
       //  }
     // };
      
     // URL urlToPage = new File(file.toString()).toURI().toURL();
      
    //pane.setPage(urlToPage);  
   // pane.print(null, null, false, service, pras, false);

   

                       
                       
                   }
                }
            }

            //scans inner folder for files
            else if (file.isDirectory()) {
                print("Folder: " + file.getName());
                sortAll(file.getPath());

            }
    }



}