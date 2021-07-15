package com.example.criminalintent

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

private val TAG = "CrimeListFragment"
class CrimeListFragment : Fragment() {

    /**
     * 호스팅 액티비티에서 구현할 인터페이스
     */

    interface Callbacks {
        fun onCrimeSelected(crimeId :UUID)
    }

    private var callbacks:Callbacks? = null

    private lateinit var crimeRecyclerView: RecyclerView
//    private var adapter: CrimeAdapter? = null livedata 사용으로 주석
    private var adapter:CrimeAdapter? = CrimeAdapter(emptyList())

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this).get(CrimeListViewModel::class.java)
    }

    //livedate사용으로 주석
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Log.d(TAG, "Total crimes : ${crimeListViewModel.crimes.size}")
//        Log.d(TAG, "Total crimes : ${crimeListViewModel.crimes}")
//
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        crimeRecyclerView.adapter = adapter

        //livedate사용으로 주석
//        updateUI()

        return view
    }

    //livedate사용으로 주석
//    private fun updateUI() {
//        val crimes = crimeListViewModel.crimes
//        adapter = CrimeAdapter(crimes)
//        crimeRecyclerView.adapter = adapter
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO){
            crimeListViewModel.insertCrime(Crime())
        }

        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            Observer { crimes ->
                crimes?.let {
                    Log.i(TAG, "Got crimes ${crimes.size}")
                    updateUI(crimes)
                }
            }
        )
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateUI(crimes: List<Crime>) {
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    private inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private lateinit var crime: Crime

        val titleTextView: TextView = view.findViewById(R.id.crime_title)
        val dateTextView: TextView = view.findViewById(R.id.crime_date)
        val solvedImageView: ImageView = view.findViewById(R.id.crime_solved)

        init {
            view.setOnClickListener(this)
        }

        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.date.toString()
            solvedImageView.visibility =
                if (crime.isSolved) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }

        override fun onClick(v: View) {
//            Log.d(TAG,"${crime.title} pressed!" )
//            Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT)

            callbacks?.onCrimeSelected(crime.id)
        }
    }


    private inner class CrimeAdapter(var crimes: List<Crime>) :
        RecyclerView.Adapter<CrimeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)

        }

        override fun getItemCount(): Int {
            if (crimes != null) {
                return crimes.size
            }

            return 0
        }


    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}