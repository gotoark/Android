package application.interlace.com.pdfusingitext;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST = 100;
    public int totalnoofpages = 1;
    public boolean counterFlag;
    EditText noofrows;
    Button btn_generate;
    String FILE_PATH;
    String name_Boy[] = {"Abinesh", "Bala", "Divakar", "Elango", "Gowtham", "Hari", "Rajesh", "Logan", "Kumaresan", "Pavan"};
    String name_Girl[] = {"Dhivya", "Priya", "Abirami", "Bhavana", "Nayanthara", "Hansika", "Geetha", "Ishu", "Yamuna", "Natasha"};
    String numbers[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",};
    Document document, document2;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
        setContentView(R.layout.activity_main);
        btn_generate = (Button) findViewById(R.id.btn_generate);
        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noofrows = (EditText) findViewById(R.id.et_no_of_rows);
                Log.d("RESULT", "-------------------------------------No of Recoreds: " + noofrows.getText().toString());
                Log.d("RESULT", "-------------------------------------Condition 1: " + noofrows.getText().toString().equals(""));
                Log.d("RESULT", "-------------------------------------Condition 2: " + noofrows.getText().toString().isEmpty());
                if (!noofrows.getText().toString().equals("") || !noofrows.getText().toString().isEmpty()) {
                    totalnoofpages = 0;
                    //set Output path
                    FILE_PATH = Environment.getExternalStorageDirectory().toString() + "/PDF/" + "TelephoneDirectory.pdf";
                    document = new Document(PageSize.A4);
                    FILE_PATH = Environment.getExternalStorageDirectory().toString() + "/PDF/" + "TelephoneDirectory.pdf";
                    document2 = new Document(PageSize.A4);
                    //Create a new Dir for PDF Files
                    String root_Dir = Environment.getExternalStorageDirectory().toString();
                    File myDir = new File(root_Dir + "/PDF");
                    myDir.mkdir();
                    //Call Pdf RendererAsynTask to write into a document

                    PdfRenderer pdfRenderer = new PdfRenderer();
                    pdfRenderer.execute(noofrows.getText().toString());


                } else {
                    noofrows.setError("Please Enter the Record Size");
                }


            }
        });


    }

    public void addMetaData(Document document) {
        Log.d("RESULT", "-------------------------------------addMetaData: ");
        document.addTitle("Title");
        document.addSubject("Subject");
        document.addKeywords("KEYWORD,KEYWORD2,KEYWORD3");
        document.addAuthor("");
        document.addCreator("");

    }

    public void requestPermissions() {
        Log.d("RESULT", "----------------------------------requestPermissions: ");
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);
    }


    public void addContent(Document document, String no_of_rows) {
        Log.d("RESULT", "----------------------------------------------addContent: ");
        //Define Fonts
        Font font_title = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD | Font.UNDERLINE, BaseColor.GRAY);
        Font font_cat = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font font_smllBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Font font_small = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

        //Heading Sections
        Paragraph heading = new Paragraph();
        heading.setFont(font_title);
        heading.setAlignment(Element.ALIGN_CENTER);
        heading.add("TELEPHONE DIRECTORY\n");

        //Creating Table
        int noofcolumns = 3;
        PdfPTable table = new PdfPTable(noofcolumns);

        // 100.0f mean width of table is same as Document size
        table.setWidthPercentage(100.0f);

        //Add Column Headings
        Paragraph columnText_0 = new Paragraph();
        columnText_0.setFont(font_cat);
        columnText_0.setAlignment(Element.ALIGN_CENTER);
        columnText_0.add("No");
        Paragraph columnText_1 = new Paragraph();
        columnText_1.setFont(font_cat);
        columnText_1.setAlignment(Element.ALIGN_CENTER);
        columnText_1.add("NAME");
        Paragraph columnText_2 = new Paragraph();
        columnText_2.setFont(font_cat);
        columnText_2.setAlignment(Element.ALIGN_CENTER);
        columnText_2.add("NUMBER");
        PdfPCell column_Title_0 = new PdfPCell(columnText_0);
        PdfPCell column_Title_1 = new PdfPCell(columnText_1);
        PdfPCell column_Title_2 = new PdfPCell(columnText_2);
        //Add table Rows
        table.addCell(column_Title_0);
        table.addCell(column_Title_1);
        table.addCell(column_Title_2);
        table.setHeaderRows(1);
        int i = 0, row;
        //Generate Random Table Contents
        while (i < Integer.parseInt(no_of_rows)) {
            row = i + 1;
            table.addCell(row + "");
            table.addCell(getRandomname());
            table.addCell(getRandomMobileNumber());
            i++;
        }


        try {
          /*  document.add(document.getPageSize());*/
            document.add(addEmptyLine(2));
            document.add(heading);
            document.add(addEmptyLine(2));
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }
//Empty Line Generator

    public Paragraph addEmptyLine(int lines) {
        int i;
        //Add Empty Line
        Paragraph emptyLines = new Paragraph();
        for (i = 0; i < lines; i++) {
            emptyLines.add(new Paragraph(" "));
        }
        return emptyLines;
    }

//Random Name Generator

    public String getRandomname() {
        return name_Boy[generateRandomNumber(0, 9)] + " " + name_Girl[generateRandomNumber(0, 9)];
    }

    public String getRandomMobileNumber() {
        int i;
        String mobilenumber = numbers[generateRandomNumber(7, 9)] + numbers[generateRandomNumber(7, 9)] + numbers[generateRandomNumber(7, 9)];
        for (i = 0; i < 6; i++) {
            mobilenumber = mobilenumber + "" + numbers[generateRandomNumber(7, 9)];
        }

        return mobilenumber;
    }

    public int generateRandomNumber(int min, int max) {
        //Generate Random Numbers
        Random g = new Random();
        return g.nextInt((max - min) + 1) + min; //Random number 0(inclusive)-9(inclusive)
    }


//HEADER AND FOOTER SECTION

    class HeaderFooter extends PdfPageEventHelper {
        Font ffont = new Font(Font.FontFamily.UNDEFINED, 10, Font.ITALIC);

        @Override
        public void onCloseDocument(PdfWriter writer, Document document) {
            counterFlag = false;
            super.onCloseDocument(writer, document);
        }

        public void onEndPage(PdfWriter writer, Document document) {
            if (counterFlag)
                totalnoofpages++;
            PdfContentByte cb = writer.getDirectContent();
            Phrase header = new Phrase("Created With Random Telephone Directory Generator by gotoark", ffont);
            Phrase footer = new Phrase(document.getPageNumber() + " of " + totalnoofpages + " Pages ", ffont);
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT,
                    header,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.top() + 10, 0);
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    footer,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 10, 0);
        }
    }

    class PdfRenderer extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Rendering the File...!! \n Please wait....");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(FILE_PATH));
                pdfWriter.setPageEvent(new HeaderFooter());
                counterFlag = true;
                Log.d("RESULT", "-------------------------------------FILE_PATH: " + FILE_PATH);
                //Open Document
                document.open();
                //User Defined Methods
                addMetaData(document);
                addContent(document, params[0]);
                document.close();
                Log.d("RESULT", "-------------------------------------OverWrite the Same Document with page Count");
                PdfWriter pdfWriter2 = PdfWriter.getInstance(document2, new FileOutputStream(FILE_PATH));
                pdfWriter2.setPageEvent(new HeaderFooter());
                document2.open();
                //User Defined Methods
                addMetaData(document2);
                addContent(document2, params[0]);
                document2.close();

            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            //  Toast.makeText(MainActivity.this, , Toast.LENGTH_SHORT).show();
            Snackbar snack_Filepath = Snackbar.make(findViewById(R.id.mainLayout),
                    "File Saved in " + FILE_PATH, Snackbar.LENGTH_LONG);
            snack_Filepath.show();


            super.onPostExecute(aVoid);
        }

    }


}

