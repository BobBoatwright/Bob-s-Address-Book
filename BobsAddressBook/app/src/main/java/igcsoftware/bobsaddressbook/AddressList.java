package igcsoftware.bobsaddressbook;


import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;


public class AddressList extends AppCompatActivity implements AddressEditDialogFragment.onDismissListener {

    private RecyclerView recyclerView;
    private static AddressCardRecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button addAddressButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);

        //insertTestCards();
        setupUI();

    }

    private void setupUI() {
        recyclerView = (RecyclerView) findViewById(R.id.address_recycler_view);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AddressCardRecyclerAdapter(AddressDao.getCards(this));
        recyclerView.setAdapter(adapter);

        addAddressButton = (Button)findViewById(R.id.add_address_button);
        addAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAddress();
            }
        });
    }

    private void insertTestCards() {
        AddressCard card1 = new AddressCard("Jenny Boatwright", "3801 Dowitcher Lane", null, "Gahanna", "Ohio", "43230");
        AddressCard card2 = new AddressCard("Bob Boatwright", "6432 E. Main Street", "IGC Software - Second Floor", "Reynoldsburg", "Ohio", "43068");
        AddressCard card3 = new AddressCard("Tom Boatwright", "34506 Fortune Court", null, "North Ridgeville", "Ohio", "44039");

        AddressDao.insertCard(this, card1);
        AddressDao.insertCard(this, card2);
        AddressDao.insertCard(this, card3);
    }

    private void addAddress() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        DialogFragment editAddress = AddressEditDialogFragment.newInstance(this);
        editAddress.show(ft, "addAddress");
    }

    @Override
    public void onDismiss() {
        setupUI();
    }

}
