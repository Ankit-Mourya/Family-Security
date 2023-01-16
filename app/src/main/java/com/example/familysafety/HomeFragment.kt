package com.example.familysafety

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment :Fragment() {
   lateinit var adpater_contact : InviteAdapter
    private val contactList:ArrayList<Contactmodel> = ArrayList()
      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
      }

      override fun onCreateView(
          inflater: LayoutInflater, container: ViewGroup?,
          savedInstanceState: Bundle?
      ): View? {
          // Inflate the layout for this fragment
          return inflater.inflate(R.layout.fragment_home, container, false)
      }

      override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
          super.onViewCreated(view, savedInstanceState)

           val listMember = listOf<Member_Model>(
              Member_Model("sam","House No- 32,Mahaveer Nagar-1, Kota,rajasthan"),
              Member_Model("jack","House no-22,Vigyan Nagar, Kota, Rajasthan"),
              Member_Model("james","House No-3C2, MahaveerNagar-3, Kota,Rajasthan")
          )

          val adapter = MemberAdapter(listMember)
          val recycler = requireView().findViewById<RecyclerView>(R.id.recycler_view)
          recycler.layoutManager = LinearLayoutManager(requireContext())
          recycler.adapter = adapter

           adpater_contact =InviteAdapter(contactList)
          fetchDatabaseContacts()
          CoroutineScope(Dispatchers.IO).launch {

              insertDatabaseContacts(fetchcontacts())

          }


          val recycler_contact = requireView().findViewById<RecyclerView>(R.id.invite)
          recycler_contact.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
          recycler_contact.adapter = adpater_contact
      }

    private fun fetchDatabaseContacts()  {
        val database = MyFamilyDatabase.getDatabase(requireContext())
        database.contactDao().getAllContacts().observe(viewLifecycleOwner){
            contactList.clear()
            contactList.addAll(it)
            adpater_contact.notifyDataSetChanged()
        }
    }

    private suspend fun insertDatabaseContacts(contactList: ArrayList<Contactmodel>) {
        val database = MyFamilyDatabase.getDatabase(requireContext())
        database.contactDao().insertAll(contactList)
    }

    @SuppressLint("Range")
    private fun fetchcontacts(): ArrayList<Contactmodel> {

        val cr = requireActivity().contentResolver
        val cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null)
        val listcontacts:ArrayList<Contactmodel> = ArrayList()

        if (cursor!=null && cursor.count>0){
            while (cursor!=null && cursor.moveToNext()){
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val hasPhoneNumber = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))

                if (hasPhoneNumber>0){
                    val pcur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = ?",
                        arrayOf(id),"")
                    if(pcur !=null &&pcur.count>0){
                        while (pcur!=null && pcur.moveToNext()){
                            val phoneNum =pcur.getString((pcur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)))

                            listcontacts.add(Contactmodel(name,phoneNum))
                        }
                        pcur.close()
                    }
                }
            }
            if (cursor!=null){
                cursor.close()
            }
        }
        return listcontacts
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()

    }
  }
