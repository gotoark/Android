package application.interlace.com.excelexample;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "Result";
    String name_Boy[] = {"Abinesh", "Bala", "Divakar", "Elango", "Gowtham", "Hari", "Rajesh", "Logan", "Kumaresan", "Pavan"};
    String name_Girl[] = {"Dhivya", "Priya", "Abirami", "Bhavana", "Nayanthara", "Hansika", "Geetha", "Ishu", "Yamuna", "Natasha"};
    String numbers[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",};
    EditText noofrows;
    Button btn_generate,btn_view;
    Boolean btn_view_lock;
    public static TextView tv_TableTitle;
    public ListView listview;
    ArrayList<Integer> al_id = new ArrayList<>();
    ArrayList<String> al_name = new ArrayList<>();
    ArrayList<String> al_number = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_generate=(Button)findViewById(R.id.btn_generate);
        btn_view=(Button)findViewById(R.id.btn_view);
        listview=(ListView)findViewById(R.id.list_people);
        tv_TableTitle=(TextView)findViewById(R.id.tv_tableTitle);
        btn_view_lock=true;
        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noofrows=(EditText)findViewById(R.id.et_no_of_rows);
                Log.i(TAG, "onClick: "+noofrows.getText().toString());
                if(!noofrows.getText().toString().equals("")){
                    if(ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                        Log.e(TAG, "------------------------Permission Granted");
                        saveAsExcel(getApplicationContext(),Integer.parseInt(noofrows.getText().toString()));
                    }else {
                        Log.e(TAG, "------------------------Permission Denied");
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                101);


                    }


                }else{
                    Snackbar snack_bar = Snackbar.make(findViewById(R.id.mainlayout),
                            "People Count Should not be an Empty One ..!!! Please Enter any Number", Snackbar.LENGTH_LONG);
                    snack_bar.show();
                    Log.e(TAG, "OnClick: ------------------------Empty Input");

                }
            }
        });
       /* btn_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!btn_view.isEnabled()){
                    readExcel(getApplicationContext(),"ExcelOutput");
                }else {
                    Snackbar snack_bar = Snackbar.make(findViewById(R.id.mainlayout),
                            "Please Generate XL FIrst and then Try again...!!", Snackbar.LENGTH_LONG);
                    snack_bar.show();
                }
                return false;
            }
        }); */  btn_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(btn_view_lock){
                    readExcel(getApplicationContext(),"ExcelOutput",listview);
                }else {
                    Snackbar snack_bar = Snackbar.make(findViewById(R.id.mainlayout),
                            "Please Generate XL FIrst and then Try again...!!", Snackbar.LENGTH_LONG);
                    snack_bar.show();
                }

            }
        });

    }

    //Store Excel File
    private boolean saveAsExcel(Context context, Integer no_of_records) {

        //Check Availabe Memory
        if (!isExternalStorageAvilable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "saveAsExcel: ------------------------Storage not Available or Read Only");
            return false;
        }
        boolean success = false;

        //Creating New WorkBook

        Workbook workbook = new HSSFWorkbook();

        //Create Cell

        Cell c = null;

        //Cell Styles for Headers

        CellStyle cellStyle_Header = workbook.createCellStyle();
        cellStyle_Header.setFillForegroundColor(HSSFColor.LIME.index);
        cellStyle_Header.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //Create a New Sheet

        Sheet sheet_One = null;
        sheet_One = workbook.createSheet("Random Telephone Directory");

        //Generate Column Headings

        Row row = sheet_One.createRow(0);
        c = row.createCell(0);
        c.setCellValue("S.No");
        c.setCellStyle(cellStyle_Header);
        c = row.createCell(1);
        c.setCellValue("Name");
        c.setCellStyle(cellStyle_Header);
        c = row.createCell(2);
        c.setCellValue("Number");
        c.setCellStyle(cellStyle_Header);


        //Adding Contents
        int i;
        for (i = 1; i <=no_of_records; i++) {
            Row rowi = sheet_One.createRow(i);
            c = rowi.createCell(0);
            c.setCellValue(i);
            c.setCellStyle(cellStyle_Header);
            c = rowi.createCell(1);
            c.setCellValue(getRandomname());
            c.setCellStyle(cellStyle_Header);
            c = rowi.createCell(2);
            c.setCellValue(getRandomMobileNumber());
            c.setCellStyle(cellStyle_Header);
        }


        //Write and Store Excel File

        File file = new File(context.getExternalFilesDir(null), "ExcelOutput" + ".xls");
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            Log.w(TAG, "Writing File -----------------------------" + file);
            Snackbar snack_bar = Snackbar.make(findViewById(R.id.mainlayout),
                    "File Saved Successfully "+file, Snackbar.LENGTH_LONG);
            snack_bar.show();
            //Enable View Button
            btn_view_lock=true;
            success = true;

        } catch (IOException e) {
            Snackbar snack_bar = Snackbar.make(findViewById(R.id.mainlayout),
                    "Error Writing File ", Snackbar.LENGTH_LONG);
            snack_bar.show();
            Log.w(TAG, "Error Writing File -----------------------------" + file);
        } catch (Exception e) {
            Snackbar snack_bar = Snackbar.make(findViewById(R.id.mainlayout),
                    "Failed to Save File", Snackbar.LENGTH_LONG);
            snack_bar.show();
            Log.w(TAG, "Failed to Save File -----------------------------" + file);
        } finally {
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();
                Log.w(TAG, "File OutputStream Closed Successfully-----------------------------" + file);
            } catch (Exception e) {
                Log.w(TAG, "Error While Closing File OutputStream -----------------------------" + file);

            }
        }
        return success;
    }

    //Read the Values from the Excel File
    public static void readExcel(Context context, String filename, ListView listview){
         ArrayList<String> al_name=new ArrayList<>();
         ArrayList<String> al_number=new ArrayList<>();
         ArrayList<String> al_id=new ArrayList<>();
        //Check Availabe Memory
        if (!isExternalStorageAvilable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "saveAsExcel: ------------------------Storage not Available or Read Only");
            return;
        }
        try {
            //Creating Input Stream
            File inputfile = new File(context.getExternalFilesDir(null), filename);
            FileInputStream myInput = new FileInputStream(inputfile+".xls");

            //Create POIFileSystem Object
            POIFSFileSystem poiFileSystem = new POIFSFileSystem(myInput);

            //Create workbook Using FileSystem
            HSSFWorkbook workbook = new HSSFWorkbook(poiFileSystem);
            HSSFSheet mySheet = workbook.getSheetAt(0);

            //Read Table Header
            CellReference cellReference=new CellReference("A1");
            Log.d(TAG, "-----------------------------Cell Value :" + cellReference.getCol());
            Row titleRow=mySheet.getRow(cellReference.getRow());
            Cell titleCell=titleRow.getCell(cellReference.getCol());
            Log.d(TAG, "-----------------------------Cell Value :" +titleCell.getStringCellValue() );

            //Set Table Title
            tv_TableTitle.setText(titleCell.getStringCellValue() );

            //Get Iterator for Row
            Iterator r_Iterator = mySheet.rowIterator();
            int count=0;
           /* while(count<6){
                r_Iterator.next();
                 count++;
            }*/

            //Iterate Over Rows
            while (r_Iterator.hasNext()) {
                HSSFRow myRow = (HSSFRow) r_Iterator.next();
                //Get Iterator for Row
                Iterator c_Iterator = myRow.cellIterator();
              //  Log.d(TAG, "----------------------------readExcel: "+r_Iterator.hasNext());
              //  Log.d(TAG, "----------------------------readExcel: "+c_iterator.hasNext());
                //Start from 8th cell
                while(count<8){
                    c_Iterator.next();
                    count++;
                }
                //Iterate Over Rows
                while (c_Iterator.hasNext()) {
                    HSSFCell myCellid = (HSSFCell) c_Iterator.next();
                    HSSFCell myCellname = (HSSFCell) c_Iterator.next();
                    HSSFCell myCellnumber = (HSSFCell) c_Iterator.next();

                    //Validation of Cells

                    if(!myCellid.toString().equals("")&&!myCellname.toString().equals("")&&!myCellnumber.toString().equals("")){
                        Log.d(TAG, "-----------------------------Cell Value Not Empty and Added to the Record:");
                        al_id.add(myCellid.toString());
                        //al_id.add(Integer.parseInt(myCellid.toString()));
                        al_name.add(myCellname.toString());
                        al_number.add(myCellnumber.toString());
                    }else {
                        Log.d(TAG, "-----------------------------Empty Cell Value Detected and Ignored the Record:");
                    }



               /* Log.d(TAG, "-----------------------------Cell Value:" + myCell.toString());*/
                }
         //       Log.d(TAG, "-------------END OF ROW ONE----------------");

            }
            ArrayList<ArrayList<String>> arrayList_Collections=new ArrayList<>();
            arrayList_Collections.add(al_id);
            arrayList_Collections.add(al_name);
            arrayList_Collections.add(al_number);


            //Get  ArrayList<ArrayList<String>> arrayList_Collections_dm=new ArrayList<>();
            ArrayList<ArrayList<String>> arrayList_Collections_dm=new ArrayList<>();

            arrayList_Collections_dm= findDuplicateNumber(arrayList_Collections);
           // ListAdapter peopleAdapter=new PeopleAdapter(context,al_id,al_name,al_number);
            ListAdapter peopleAdapter=new PeopleAdapter(context,arrayList_Collections_dm.get(0),arrayList_Collections_dm.get(1),arrayList_Collections_dm.get(2));
            listview.setAdapter(peopleAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static boolean isExternalStorageAvilable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
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

   //Custom Adapter for  People List

    public static class PeopleAdapter extends BaseAdapter{
        /*
        Important
        public int getCount() {
            return 0;
        } must return al_number.size(); othervise list will not be displayed in Custom Adapter

         */


        PeopleViewHolder people_modal;
        private Context context;
        private ArrayList<String> al_name;
        private ArrayList<String> al_number;
        private ArrayList<String> al_id;
        private LayoutInflater layoutinflater = null;

        public PeopleAdapter(Context c, ArrayList<String> al_id, ArrayList<String> al_name, ArrayList<String> al_number) {
            this.context = c;
            this.al_name = al_name;
            this.al_number = al_number;
            this.al_id = al_id;
            layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Log.i("RESULT", "-----------------------PeopleAdapter Initialized");

        }
        @Override
        public int getCount() {
            return al_number.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            people_modal = new PeopleViewHolder();
            View view;
            view = layoutinflater.inflate(R.layout.people_list, null);
            people_modal.tv_id = (TextView) view.findViewById(R.id.sr_no);
            people_modal.tv_username = (TextView) view.findViewById(R.id.name);
            people_modal.tv_number = (TextView) view.findViewById(R.id.number);
          //  Log.i("RESULT", "-----------------------GET Views");
            //Assign Values
            Log.i("RESULT", "-----------------------ID :"+al_id.get(position));
            people_modal.tv_username.setText(al_name.get(position));
            people_modal.tv_number.setText(al_number.get(position));
            people_modal.tv_id.setText(al_id.get(position));
         //   Log.i("RESULT", "-----------------------Values Assigned");
            return view;
        }
    }
    public static class PeopleViewHolder {
        TextView tv_id;
        TextView tv_username;
        TextView tv_number;
    }

    public static ArrayList<ArrayList<String>>  findDuplicateNumber(ArrayList<ArrayList<String>> al_collections){
         ArrayList<String> al_name_dm=new ArrayList<>();
         ArrayList<String> al_number_dm=new ArrayList<>();
         ArrayList<String> al_id_dm=new ArrayList<>();
         ArrayList<String> al_name_t=new ArrayList<>();
         ArrayList<String> al_number_t=new ArrayList<>();
         ArrayList<String> al_id_t=new ArrayList<>();

        al_id_t=al_collections.get(0);
        al_name_t=al_collections.get(1);
        al_number_t=al_collections.get(2);

        Map<String,Integer> duplicate_name_count=new HashMap<String, Integer>();
         for(String str:al_collections.get(2)){
            if(duplicate_name_count.containsKey(str)){
                duplicate_name_count.put(str,duplicate_name_count.get(str)+1);
            }else {
                duplicate_name_count.put(str,1);
            }
        }
        //Remove Repeated elements
        for(Map.Entry<String,Integer> entry:duplicate_name_count.entrySet()){
            System.out.println(entry.getKey() + " = " + entry.getValue());
            al_number_dm.add(entry.getKey());
            al_name_dm.add(al_name_t.get(al_number_t.indexOf(entry.getKey())));
            al_id_dm.add(al_id_t.get(al_number_t.indexOf(entry.getKey())));
        }


        ArrayList<ArrayList<String>> arrayList_Collections_dm=new ArrayList<>();
        arrayList_Collections_dm.add(al_id_dm);
        arrayList_Collections_dm.add(al_name_dm);
        arrayList_Collections_dm.add(al_number_dm);
        return  arrayList_Collections_dm;
    }

}
