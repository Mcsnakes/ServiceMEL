package sp.fr.servicemel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailPharmacie extends AppCompatActivity implements View.OnClickListener{

    private TextView nameAfficher;
    private TextView horaireAfficher;

    private ImageView iconeGoogleMap;

    private String name;
    private String type;
    private String horaire;
    private Double latitude;
    private Double longitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pharmacie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Récupération des données pâsser à l'activité
        Bundle params = getIntent().getExtras();

        name = params.getString("name");
        type = params.getString("type");
        horaire = params.getString("horaire");

        latitude = params.getDouble("latitude");
        longitude = params.getDouble("longitude");

        //Récupération des elements du xml
        nameAfficher = findViewById(R.id.textViewNamePharmacie);
        horaireAfficher = findViewById(R.id.textViewHorairePharmacie);
        iconeGoogleMap = findViewById(R.id.imageViewGotToMap);

        //assignation des valeurs à leurs champs
        nameAfficher.setText(name);
        horaireAfficher.setText(horaire);

        //Mise en place de l'écouteur
        iconeGoogleMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //Création d'un intent pour l'affichage de la carte
        Intent mapIntent = new Intent(this, MapsActivity.class);

        //Passage des paramètres à l'intention
        mapIntent.putExtra("latitude", latitude );
        mapIntent.putExtra("longitude", longitude );

        startActivity(mapIntent);

    }
}
