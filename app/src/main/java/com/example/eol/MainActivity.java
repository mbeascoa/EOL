package com.example.eol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private NoteAdapter mNoteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_original);


        mNoteAdapter = new NoteAdapter();
        ListView listNote = (ListView) findViewById(R.id.list_view);
        listNote.setAdapter(mNoteAdapter);

        listNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemPos, long id) {
                //Recuperamos la nota de la posición pulsada por el usuario
                Note tempNote = mNoteAdapter.getItem(itemPos);

                //Creamos una instancia de show note
                DialogShowNote dialog = new DialogShowNote();
                dialog.sendNoteSelected(tempNote);
                dialog.show(getFragmentManager(),"");
            }
        });






    }


    public void createNewNote(Note newNote){
        //Este método, recibirá una nueva nota creada por el diálogo pertinente...
        mNoteAdapter.addNote(newNote);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_add) {
            //Aquí debemos invocar una nueva instancia del diálogo para crear notas
            DialogNewNote dialog = new DialogNewNote();
            //Mostramos ese diálogo a través del manager
            dialog.show(getFragmentManager(),"note_create");
        }


        if (item.getItemId() == R.id.action_setings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }


        return false;
    }





public class NoteAdapter extends BaseAdapter {

    List<Note> noteList = new ArrayList<Note>();

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Note getItem(int itemPos) {
        return noteList.get(itemPos);
    }

    @Override
    public long getItemId(int itemPos) {
        return itemPos;
    }

    @Override
    public View getView(int itemPos, View view, ViewGroup viewGroup) {

        //Aquí programaremos la lógica de las celdas de la lista
        if (view == null){
            //La vista todavía no ha sido accedida anteriormente
            //así que lo primero que hay que hacer es inflarla
            //a partir del layout list_item.xml
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, viewGroup, false);
        }

        //Cuando estamos aquí, ya tenemos la vista bien definida
        //cargamos todos los widgets del layout

        TextView textViewTitle = (TextView) view.findViewById(R.id.text_view_title);
        TextView textViewDescription = (TextView) view.findViewById(R.id.text_view_description);

        ImageView ivImportant = (ImageView) view.findViewById(R.id.image_view_important);
        ImageView ivTodo = (ImageView) view.findViewById(R.id.image_view_todo);
        ImageView ivIdea = (ImageView) view.findViewById(R.id.image_view_idea);

        //y podemos proceder a ocultar las imágenes que sobren del layout...
        //y rellenar título y descripción de la tarea

        Note currentNote = noteList.get(itemPos);

        if (!currentNote.isImportant()){
            ivImportant.setVisibility(View.GONE);
        }
        if (!currentNote.isTodo()){
            ivTodo.setVisibility(View.GONE);
        }
        if (!currentNote.isIdea()){
            ivIdea.setVisibility(View.GONE);
        }


        textViewTitle.setText(currentNote.getTitle());
        textViewDescription.setText(currentNote.getDescription());

        return view;
    }


    public void addNote(Note n){
        noteList.add(n);
        notifyDataSetChanged();
    }

    }
}
