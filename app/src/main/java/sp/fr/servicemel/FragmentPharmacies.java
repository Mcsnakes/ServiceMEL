package sp.fr.servicemel;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sp.fr.servicemel.model.Pharmacies;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPharmacies extends Fragment implements AdapterView.OnItemClickListener {

    private List<Pharmacies> pharmaciesList;
    private ListView pharmaciesListView;

    public FragmentPharmacies() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDataFromHttp();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pharmacies, container, false);

        pharmaciesListView = view.findViewById(R.id.pharmaciesListView);
        pharmaciesListView.setOnItemClickListener(this);

        return view;
    }

    private void processResponse(String response) {

        //Transformation de la reponse en liste de pharmaciesList
        pharmaciesList = responseToList(response);

        //Conversion de la liste des pharmaciesList en un tableau de String
        //Uniquement le nom de la pharmacies
        String[] data = new String[pharmaciesList.size()];
        for (int i = 0; i < pharmaciesList.size(); i++) {

            data[i] = pharmaciesList.get(i).getName();

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getActivity(),
                android.R.layout.simple_expandable_list_item_1,
                data
        );

        pharmaciesListView.setAdapter(adapter);


    }

    private void getDataFromHttp() {

        String url = "https://opendata.lillemetropole.fr/api/records/1.0/search/?dataset=pharmacies";

        //Définition de la requête
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                //Gestion du succès
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("HTTP : ", response);
                        processResponse(response);
                    }
                },
                //Gestion d'erreur
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("HTTP : ", error.getMessage());
                    }
                }

        );

        //Ajout de la requête à la file d'exécution
        Volley.newRequestQueue(this.getActivity()).add(request);

    }

    private List<Pharmacies> responseToList(String response) {

        List<Pharmacies> list = new ArrayList<>();

        try {

            //JSONArray jsonPharmacies = new JSONArray(response);

            // On récupère le JSON complet
            JSONObject jsonObject = new JSONObject(response);

            // On récupère le tableau d'objets
            JSONArray array = new JSONArray(jsonObject.getString("records"));

            JSONObject items;
            for (int i = 0; i < array.length(); i++) {

                // On récupère un objet JSON du tableau
                JSONObject obj = new JSONObject(array.getString(i));


                //Création d'une nouvelle pharmacie
                Pharmacies pharmaUser = new Pharmacies();

                //Hydratation de la pharmacies

                //Extraction du nom contenu dans le field
                items = (JSONObject) array.get(i);
                JSONObject field = items.getJSONObject("fields");


                if(field.has("name")) {
                    pharmaUser.setName((String) field.get("name") );
                } else {
                    pharmaUser.setName("inconnu");
                }
                if(field.has("opening_hours")) {
                    pharmaUser.setHoraire((String) field.get("opening_hours"));
                } else {
                    pharmaUser.setHoraire("inconnu");
                }
                if(field.has("datasetid")) {
                    pharmaUser.setTypeBat(obj.getString("datasetid"));
                } else {
                    pharmaUser.setTypeBat("inconnu");
                }

                //Extraction des coordonnées gps
                JSONArray geo = items.getJSONObject("geometry").getJSONArray("coordinates");
                pharmaUser.setLatitude(geo.getDouble(0));
                pharmaUser.setLongitude(geo.getDouble(1));



                list.add(pharmaUser);

            }


        } catch (JSONException ex) {


        }

        return list;
    }


    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {



        //Récupération de la pharmacies sur laquel on vient de cliquer
        Pharmacies selectedPharmacies = this.pharmaciesList.get(position);

        Toast.makeText(getActivity(), "P : " + selectedPharmacies.getName(), Toast.LENGTH_LONG).show();

        //Création d'un intent pour afficher la carte



    }

}
