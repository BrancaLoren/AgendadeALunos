package com.example.ocean.agenda.converter;

import com.example.ocean.agenda.modelo.Aluno;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

/**
 * Created by aluno on 11/07/2016.
 */
public class AlunoConverter {
    public String converteParaJson(List<Aluno> alunos) {
        JSONStringer json = new JSONStringer();
        try {
            json.object().key("list").array().object().key("aluno").array();
            for (Aluno aluno : alunos){
                json.object();
                json.key("nome").value(aluno.getNome());
                json.key("nota").value(aluno.getNota());
                json.endObject();
            }
            json.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}
