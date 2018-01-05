package thinkers.com.marvin.Screens.Util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import thinkers.com.marvin.R;

import static android.R.attr.description;
import static android.R.attr.id;
import static android.R.attr.level;
import static android.R.attr.type;

public class MoviesDetails extends AppCompatActivity {
    TextView id,title,description,type,level,created,updated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);
        Intent intent=getIntent();
        id=(TextView)findViewById(R.id.id);
        title=(TextView)findViewById(R.id.title);
        description=(TextView)findViewById(R.id.description);
        type=(TextView)findViewById(R.id.type);
        level=(TextView)findViewById(R.id.level);
        created=(TextView)findViewById(R.id.created);
        updated=(TextView)findViewById(R.id.updated);

        id.setText(intent.getStringExtra("issue_id"));
        title.setText(intent.getStringExtra("issue_title"));
        description.setText(intent.getStringExtra("issue_description"));
        level.setText(intent.getStringExtra("issue_level"));
        if(intent.getStringExtra("issue_title")==null){
            title.setText("Not Yet Rated");
        }
        if(intent.getStringExtra("issue_type")==null){
            type.setText("Not Yet Rated");
        }
        type.setText(intent.getStringExtra("issue_type"));
        created.setText(intent.getStringExtra("issue_createdDate"));
        updated.setText(intent.getStringExtra("issue_updated_date"));
    }
}
