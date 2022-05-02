package com.example.excelreadtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    class CsvHelper(private val filePath: String)
    { fun readAllCsvData(fileName: String) : List<Array<String>>
        { return try { FileReader("$filePath/$fileName").use
        { fr -> //readAll()을 이용해 데이터 읽기 CSVReader(fr).use { it.readAll() } } } catch (e: IOException) { if (BuildConfig.DEBUG) { e.printStackTrace() } listOf() } } }
    
    }
