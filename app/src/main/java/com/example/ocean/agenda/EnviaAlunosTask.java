package com.example.ocean.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.ocean.agenda.converter.AlunoConverter;
import com.example.ocean.agenda.dao.AlunoDAO;
import com.example.ocean.agenda.modelo.Aluno;

import java.util.List;

/**
 * Created by aluno on 12/07/2016.
 */
public class EnviaAlunosTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context){
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
       dialog = ProgressDialog.show(context, "Aguarde", "Enviando Alunos...", true , true);
    }

    @Override
    protected String doInBackground(Void... params) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converteParaJson(alunos);



        WebClient client =new WebClient();
        String resposta = client.post(json);

        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();

    }
}
