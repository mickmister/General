using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Data.SqlClient;

namespace FakeNameSpace
{
    //This class gets labor, material, email addresses, job descriptions, and comments
    //The info to show the customer comes from here
    //It also retrieves and inputs email addresses
    public class GetFinalInfo : IGetFinalInfo
    {
        string connectionString = "SAMPLE CONNECTION STRING";

        public List<List<String>> GetAllInfoMethod(string orderNumber)
        {
            List<List<String>> mylist = new List<List<String>>();
            

            List<String> jobInfo = GetLaborInfoMethod(orderNumber);
            mylist.Add(jobInfo);
            
            List<String> materialInfo = GetMaterialInfoMethod(orderNumber);
            mylist.Add(materialInfo);
            
            List<String> comments = GetFinalCommentsMethod(orderNumber);
            mylist.Add(comments);
            
            List<String> emails = GetEmailMethod(orderNumber);
            mylist.Add(emails);

            return mylist;
        }

        public List<String> GetLaborInfoMethod(string orderNumber)
        {
            List<String> mylist = new List<String>();
            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                conn.Open();
                string whereQuery = " where Order_No = @Order_No and Type = 'LABOR' and Print_On_Invoice = 'True'";
                string cmdStr = String.Format("Select Labor_Hours,Tech_Name from Custom" + whereQuery);
                SqlCommand cmd = new SqlCommand(cmdStr, conn);
                cmd.Parameters.AddWithValue("@Order_No",orderNumber);
                SqlDataReader rd = cmd.ExecuteReader();

                if (rd.HasRows)
                {
                    while (rd.Read())
                    {
                        string hours = NewInfo.DenullifyDecimal(rd, "Labor_Hours", 0, true);
                        string technician = NewInfo.DenullifyString(rd, "Tech_Name", 1, true);
                        mylist.Add(hours); //Hours
                        mylist.Add(technician); //Technician
                    }
                }
                rd.Close();
                conn.Close();
            }
            return mylist;
        }
        public List<String> GetMaterialInfoMethod(string orderNumber)
        {
            List<String> mylist = new List<String>();
            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                conn.Open();
                string whereQuery = " where Order_No = @Order_No and Type = 'MATERIAL' and Print_On_Invoice = 'True'";
                string cmdStr = String.Format("Select Material_Quantity,Manufacturer,Part_No,Material_Description from Custom" + whereQuery);
                SqlCommand cmd = new SqlCommand(cmdStr, conn);
                cmd.Parameters.AddWithValue("@Order_No",orderNumber);
                SqlDataReader rd = cmd.ExecuteReader();

                if (rd.HasRows)
                {
                    while (rd.Read())
                    {
                        string quantity = NewInfo.DenullifyDecimal(rd, "Material_Quantity", 0, true);
                        string manufacturer = NewInfo.DenullifyString(rd, "Manufacturer", 1, true);
                        string partNumber = NewInfo.DenullifyString(rd, "Part_No", 2, true);
                        string materialDesc = NewInfo.DenullifyString(rd, "Material_Description", 3, true);
                        mylist.Add(quantity); //Quantity
                        mylist.Add(manufacturer); //Manufacturer
                        mylist.Add(partNumber); //Part Number
                        mylist.Add(materialDesc); //Description
                    }
                }
                rd.Close();
                conn.Close();
            }
            return mylist;
        }
        public List<String> GetEmailMethod(string orderNumber)
        {
            List<String> emails = new List<String>();

            using (SqlConnection conn = new SqlConnection(connectionString)
            {
                conn.Open();
                string whereQuery = " where Order_No = @Order_No and Submission_No = 1";
                string cmdStr = String.Format("Select Email_Address_1, Email_Address_2, Email_Address_3 from Custom_Header" + whereQuery);
                SqlCommand cmd = new SqlCommand(cmdStr, conn);
                cmd.Parameters.AddWithValue("@Order_No",orderNumber);
                SqlDataReader rd = cmd.ExecuteReader();

                if (rd.HasRows)
                {
                    while (rd.Read())
                    {
                        if (!rd.IsDBNull(rd.GetOrdinal("Email_Address_1"))) emails.Add(rd.GetString(0));
                        if (!rd.IsDBNull(rd.GetOrdinal("Email_Address_2"))) emails.Add(rd.GetString(1));
                        if (!rd.IsDBNull(rd.GetOrdinal("Email_Address_3"))) emails.Add(rd.GetString(2));             
                    }
                }
                rd.Close();
                conn.Close();
            }
            return emails;
        }
        public bool InsertEmailMethod(string orderNumber, string email){
            bool success = false;
            bool email2Bool = false;
                using (SqlConnection conn = new SqlConnection(connectionString))
                {
                conn.Open();
                String whereQuery = " where Order_No = @Order_No";
                string cmdStr = String.Format("Select Email_Address_2 from Custom_Header" + whereQuery);
                SqlCommand cmd = new SqlCommand(cmdStr, conn);
                cmd.Parameters.AddWithValue("@Order_No",orderNumber);
                SqlDataReader rd = cmd.ExecuteReader();

                if (rd.HasRows)
                {
                    while (rd.Read())
                    {
                        if (!rd.IsDBNull(rd.GetOrdinal("Email_Address_2"))) email2Bool = true;
                    }
                }
                rd.Close();
                String emailChoice = "";
                    if(!email2Bool){
                        emailChoice = "Email_Address_2";
                    }
                    else{
                        emailChoice = "Email_Address_3";
                    }
                    cmd = new SqlCommand("update Custom_Header set @emailChoice = @email" + whereQuery, conn);
                    cmd.Parameters.AddWithValue("@email", email);
                    cmd.Parameters.AddWithValue("@emailChoice", emailChoice);

                    int result = cmd.ExecuteNonQuery();
                    if (result == 1)
                    {
                        success = true;
                    }
                    else
                    {
                        success = false;
                    }

                    conn.Close();
                };
            
            return success;
        }



        public List<String> GetFinalCommentsMethod(string orderNumber)
        {
            List<String> mylist = new List<String>();
            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                conn.Open();                
                DateTime startDate = System.Convert.ToDateTime("1/1/1753 12:00:00 AM");
                string whereQuery = " where Order_No = @Order_No and Print_On_Invoice = 'True'";
                string cmdStr = String.Format("Select Date,Comment from Custom_Comment" + whereQuery);
                SqlCommand cmd = new SqlCommand(cmdStr, conn);
                cmd.Parameters.AddWithValue("@Order_No",orderNumber);
                SqlDataReader rd = cmd.ExecuteReader();
                if (rd.HasRows)
                {
                    while (rd.Read())
                    {

                        if (rd.GetDateTime(0) != startDate)
                        {
                            mylist.Add("|" + rd.GetDateTime(0).ToShortDateString().ToString()); //Date of Comment
                        }
                        mstring comment = NewInfo.DenullifyString(rd, "Comment", 1, true);
                        mylist.Add(comment); //Comment
                    }
                }
                rd.Close();

                conn.Close();
            }
            return mylist;
        }
    }
}
