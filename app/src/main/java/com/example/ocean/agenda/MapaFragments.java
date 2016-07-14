package com.example.ocean.agenda;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.example.ocean.agenda.dao.AlunoDAO;
import com.example.ocean.agenda.modelo.Aluno;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by aluno on 13/07/2016.
 */
public class MapaFragments extends SupportMapFragment implements OnMapReadyCallback {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng posicaodaescola = pegaCoordenadaDoEnd("Rua vergueiro 3185, Vila Mariana , Sao paulo");
        if(posicaodaescola != null){
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaodaescola, 17);
            googleMap.moveCamera(update);
        }

        AlunoDAO alunoDAO =new AlunoDAO(getContext());
        for (Aluno aluno : alunoDAO.buscaAlunos()){
            LatLng coordenada = pegaCoordenadaDoEnd(aluno.getEndereco());
            if(coordenada != null){
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coordenada);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }
        }
        alunoDAO.close();

        new Localizador(getContext(), googleMap);
    }

    private LatLng pegaCoordenadaDoEnd(String endereco){
        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> resultados = geocoder.getFromLocationName(endereco,1);
            if(!resultados.isEmpty()){
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return posicao;
            }

        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
}

