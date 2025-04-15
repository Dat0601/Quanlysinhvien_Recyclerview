package com.example.quanlysinhvien_recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val context: Context,
    private val studentList: MutableList<Student>
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var onStudentListener: OnStudentListener? = null

    interface OnStudentListener {
        fun onDeleteClick(position: Int)
    }

    fun setOnStudentListener(listener: OnStudentListener) {
        this.onStudentListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = studentList[position]
        holder.tvName.text = student.name
        holder.tvId.text = student.id

        holder.btnDelete.setOnClickListener {
            onStudentListener?.onDeleteClick(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int = studentList.size

    fun addStudent(student: Student) {
        studentList.add(0, student)
        notifyItemInserted(0)
    }

    fun removeStudent(position: Int) {
        if (position >= 0 && position < studentList.size) {
            studentList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_student_name)
        val tvId: TextView = itemView.findViewById(R.id.tv_student_id)
        val btnDelete: Button = itemView.findViewById(R.id.btn_delete)
    }
}