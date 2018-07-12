package com.beatutify.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.beatutify.R;
import com.beatutify.model.Employee;
import com.rey.material.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan.kalsi on 3/22/2018.
 */

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.EmployeeHolder> {

    private int selectedEmployeeIndex = 0;
    private List<Employee> employeeList = new ArrayList<>();
    private List<Employee> unfilteredEmployeeList = new ArrayList<>();

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        unfilteredEmployeeList = employeeList;
        this.employeeList.clear();
        this.employeeList.addAll(employeeList);
        notifyDataSetChanged();
    }

    @Override
    public EmployeeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_list_item, parent, false);
        return new EmployeeHolder(view);
    }

    @Override
    public void onBindViewHolder(EmployeeHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class EmployeeHolder extends RecyclerView.ViewHolder {
        TextView employeeNameTv;
        RadioButton employeeRadioBtn;
        int position;

        public EmployeeHolder(View itemView) {
            super(itemView);
            employeeNameTv = itemView.findViewById(R.id.employee_name_tv);
            employeeRadioBtn = itemView.findViewById(R.id.employee_radio_btn);
            employeeRadioBtn.setEnabled(false);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    employeeRadioBtn.setChecked(!employeeRadioBtn.isChecked());
                    int oldSelectedIndex = selectedEmployeeIndex;
                    if (employeeRadioBtn.isChecked()) {
                        selectedEmployeeIndex = position;
                    }

                    if (oldSelectedIndex!=selectedEmployeeIndex)
                        notifyItemChanged(oldSelectedIndex,false);

                }
            });

        }

        public void bind(int position) {
            this.position = position;
            employeeRadioBtn.setChecked(selectedEmployeeIndex == position);
            employeeNameTv.setText(employeeList.get(position).getEmployeeName());
        }
    }

    public Employee getSelectedEmployee() {
        if (employeeList.size() > selectedEmployeeIndex)
            return employeeList.get(selectedEmployeeIndex);
        else
            return null;
    }

    public void filter(String query) {
        Employee selectedEmployee = null;
        if (employeeList.size() > selectedEmployeeIndex)
            selectedEmployee = employeeList.get(selectedEmployeeIndex);
        employeeList.clear();
        selectedEmployeeIndex = 0;
        int index = 0;
        for (Employee employee : unfilteredEmployeeList) {
            if (employee.getEmployeeName().indexOf(query) >= 0) {
                employeeList.add(employee);
              /*  if (employee.equals(selectedEmployee))
                    selectedEmployeeIndex = index;
*/
            }

            index++;
        }

        notifyDataSetChanged();

    }
}
