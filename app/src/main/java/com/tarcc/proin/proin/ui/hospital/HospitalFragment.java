package com.tarcc.proin.proin.ui.hospital;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.FragmentListViewBinding;
import com.tarcc.proin.proin.model.Hospital;

import java.util.ArrayList;



public class HospitalFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static HospitalFragment newInstance() {

        Bundle args = new Bundle();

        HospitalFragment fragment = new HospitalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentListViewBinding binding;
    private HospitalAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new HospitalAdapter(getActivity(), new ArrayList<Hospital>());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_view, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.listView.setAdapter(adapter);
        adapter.addAll(getHospitals());

        binding.listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        HospitalDetailsActivity.start(getActivity(), adapter.getItem(i));
    }

    private ArrayList<Hospital> getHospitals() {
        ArrayList<Hospital> hospitals = new ArrayList<>();
        hospitals.add(getHospital1());
        hospitals.add(getHospital2());
        hospitals.add(getHospital3());
        hospitals.add(getHospital4());
        hospitals.add(getHospital5());
        hospitals.add(getHospital6());
        hospitals.add(getHospital7());
        hospitals.add(getHospital8());
        hospitals.add(getHospital9());

        return hospitals;
    }

    private Hospital getHospital1() {
        Hospital hospital = new Hospital()
                .setName("Columbia Asia Hospital")
                .setAddress("No. 1, Jalan Danau Saujana Off Jalan Genting Klang, 53300 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur")
                .setDescription("Columbia Asia Hospital – Setapak is a multi-specialty hospital easily accessible from Jalan Genting Klang, DUKE Highway and Taman Melati, making it a significant landmark in this major suburb of Kuala Lumpur. The 84-bed hospital in Setapak, Kuala Lumpur serves its surrounding residential areas such as Danau Kota Township, Taman Sri Rampai and Wangsa Maju. The hospital is easily accessible from Jalan Genting Klang, DUKE Highway and also the MRR2 (Taman Melati).")
                .setImageUrl("https://www.columbiaasia.com/malaysia/sites/default/files/hospital/hospitals-setapak-overview_0.jpg")
                .setLat(3.201290)
                .setLng(101.718003)
                .setOperationHours("24 hours");
        return hospital;
    }

    private Hospital getHospital2() {
        Hospital hospital = new Hospital()
                .setName("Hospital Kuala Lumpur")
                .setAddress("23, Jalan Pahang, 53000 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur.")
                .setDescription("Kuala Lumpur Hospital is a Malaysian government-owned public hospital in Kuala Lumpur, the capital city of Malaysia. Founded in 1870, HKL is a not-for-profit institution and serves as the flagship hospital of the Malaysian public healthcare.")
                .setImageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Kuala_Lumpur_Hospital.JPG/1200px-Kuala_Lumpur_Hospital.JPG")
                .setLat(3.171883)
                .setLng(101.700219)
                .setOperationHours("24 hours");
        return hospital;
    }

    private Hospital getHospital3() {
        Hospital hospital = new Hospital()
                .setName("KPJ Sentosa KL")
                .setAddress("36 Jalan Cemor, Kompleks Damai, 50400 Kuala Lumpur, Malaysia.")
                .setDescription("KPJ Sentosa KL Specialist Hospital is subject to the personal data protection principles under the Personal Data Protection Act 2010 (hereafter referred to as PDPA) with effect from 15 November 2013, which regulates the processing of personal data in commercial transactions. The terms \"personal data\", \"processing\" and \"commercial transactions\" shall have the meaning provided in the PDPA. ")
                .setImageUrl("https://www.kpjhealth.com.my/eventpics/21.jpg")
                .setLat(3.170830)
                .setLng(101.697360)
                .setOperationHours("24 hours");
        return hospital;
    }

    private Hospital getHospital4() {
        Hospital hospital = new Hospital()
                .setName("Gleneagles Kuala Lumpur")
                .setAddress("Block A & Block B, 286 & 288, Jalan Ampang, 50450 Kuala Lumpur, Federal Territory of Kuala Lumpur.")
                .setDescription("Gleneagles Kuala Lumpur is a hospital in Kuala Lumpur, Malaysia. The hospital was funded in 1996, and commenced its operations on 1 August 1996. GKL is a subsidiary of Pantai Medical Centre Sdn Bhd. ")
                .setImageUrl("http://pic.sini.com.my/ROOT/busiImage/dest/health/2015/5/8/20150508173830513.jpg")
                .setLat(3.161263)
                .setLng(101.739178)
                .setOperationHours("24 hours");
        return hospital;
    }

    private Hospital getHospital5() {
        Hospital hospital = new Hospital()
                .setName("HSC Medical Center")
                .setAddress("5-1, Menara HSC, 187, Jalan Ampang, Taman U Thant, 50450 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur")
                .setDescription("HSC Medical Center is a private hospital in Kuala Lumpur, Malaysia, the capital of Malaysia. It offers comprehensive health screening packages, outpatient balloon angioplasty and stent implantation for patients throughout the world.")
                .setImageUrl("https://www.hsc.com.my/pic/banner-menara.jpg")
                .setLat(3.159993)
                .setLng(101.722855)
                .setOperationHours("24 hours");
        return hospital;
    }

    private Hospital getHospital6() {
        Hospital hospital = new Hospital()
                .setName("KPJ Tawakkal Specialist Hospital")
                .setAddress("1, Jalan Pahang Barat, Pekeliling, 53000 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur.")
                .setDescription("KPJ Tawakkal Specialist Hospital (KPJ TSH) located in the heart of Kuala Lumpur, is member of the KPJ Healthcare Berhad Group. With a capacity of 200 beds and providing a full range of healthcare services by 70 specialist consultants and supported by over 800 employees, we serve more than 17,000 inpatients and over 180,000 outpatients a year of which approximately 5% are international patients.")
                .setImageUrl("http://www.malaysiacentral.com/information-directory/wp-content/uploads/2013/09/kpj-tawakkal-specialist-hospital-private-hospital-and-medical-facilities-in-kuala-lumpur-malaysia.jpg")
                .setLat(3.176951)
                .setLng(101.698642)
                .setOperationHours("24 hours");
        return hospital;
    }

    private Hospital getHospital7() {
        Hospital hospital = new Hospital()
                .setName("Prince Court Medical Centre")
                .setAddress("39, Jalan Kia Peng, Kuala Lumpur, 50450 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur.")
                .setDescription("Prince Court Medical Centre is a 270-bed private healthcare facility located in the heart of Kuala Lumpur, Malaysia. Our aim is to be one of the leading healthcare providers in Asia, offering comprehensive medical care to the highest international standards through world-class facilities, innovative technology & excellent customer services.")
                .setImageUrl("http://www.hospitals-malaysia.org/portal/images/gallery/29_princecourt.jpg")
                .setLat(3.148968)
                .setLng(101.721831)
                .setOperationHours("24 hours");
        return hospital;
    }

    private Hospital getHospital8() {
        Hospital hospital = new Hospital()
                .setName("Damai Service Hospital HQ")
                .setAddress("109-119, Jalan Sultan Azlan Shah, Titiwangsa Sentral, 51200 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur.")
                .setDescription("Established in 1981, the Damai Service Hospital (HQ) is a premier Malaysian hospital group offering secondary level healthcare. It was built on the Vision of the Founder Dr. Guna Sittampalam of providing “Quality Healthcare at Affordable Cost to Everyone” in every aspect of its services. Our hospital has a full range of diagnostic and therapeutic facilities capable of treating up to 95% of the spectrum of illnesses. ")
                .setImageUrl("http://www.malaysiacentral.com/information-directory/wp-content/uploads/2013/10/damai-service-hospital-hq-private-hospital-and-medical-facilities-in-jalan-ipoh-kuala-lumpur-malaysia.jpg")
                .setLat(3.170126)
                .setLng(101.695191)
                .setOperationHours("24 hours");
        return hospital;
    }

    private Hospital getHospital9() {
        Hospital hospital = new Hospital()
                .setName("Tung Shin Hospital Western Medical Department")
                .setAddress("102, Jalan Pudu, Bukit Bintang, 55100 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur.")
                .setDescription("Tung Shin Hospital, previously known as Pooi Shin Thong, was founded in 1881 by Kapitan Cina Yap Kwan Seng, and located at Sultan Street, Kuala Lumpur. Initially, it merely provided traditional medical care for patients.")
                .setImageUrl("http://www.tungshin.com.my/wp-content/uploads/2012/07/TungShin_WMD.png")
                .setLat(3.146535)
                .setLng(101.703983)
                .setOperationHours("24 hours");
        return hospital;
    }

}
