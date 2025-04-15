package com.example.quanlysinhvien_recyclerview

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), StudentAdapter.OnStudentListener {

    private lateinit var rvStudents: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var studentAdapter: StudentAdapter
    private val studentList = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        rvStudents = findViewById(R.id.rv_students)
        fabAdd = findViewById(R.id.fab_add)

        // Initialize student data
        loadSampleData()

        // Setup RecyclerView
        studentAdapter = StudentAdapter(this, studentList)
        studentAdapter.setOnStudentListener(this)
        rvStudents.layoutManager = LinearLayoutManager(this)
        rvStudents.adapter = studentAdapter

        // Set click listener for FAB
        fabAdd.setOnClickListener {
            showAddStudentDialog()
        }
    }

    private fun loadSampleData() {
        // Add some sample data
        studentList.add(Student("SV001", "Nguyễn Văn A"))
        studentList.add(Student("SV002", "Trần Thị B"))
        studentList.add(Student("SV003", "Lê Văn C"))
        studentList.add(Student("SV004", "Phạm Thị D"))
        studentList.add(Student("SV005", "Hoàng Văn E"))
    }

    private fun showAddStudentDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_student)
        dialog.setTitle("Thêm sinh viên mới")

        val etName = dialog.findViewById<EditText>(R.id.et_name)
        val etId = dialog.findViewById<EditText>(R.id.et_id)
        val btnSave = dialog.findViewById<Button>(R.id.btn_save)
        val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel)

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val id = etId.text.toString().trim()

            if (name.isEmpty() || id.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Add new student to the list
            val newStudent = Student(id, name)
            studentAdapter.addStudent(newStudent)

            // Scroll to the top to see the new item
            rvStudents.smoothScrollToPosition(0)

            // Dismiss dialog
            dialog.dismiss()
            Toast.makeText(this, "Đã thêm sinh viên mới", Toast.LENGTH_SHORT).show()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDeleteClick(position: Int) {
        studentAdapter.removeStudent(position)
        Toast.makeText(this, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show()
    }
}