package igcsoftware.bobsaddressbook;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Recycler Adapter for use with Bob's Address Book
 *
 * Created by BobSSD on 6/5/2016.
 */
public class AddressCardRecyclerAdapter extends RecyclerView.Adapter<AddressCardRecyclerAdapter.CardViewHolder> {
    private List<AddressCard> cards;

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView contactName;
        public TextView address;
        public TextView address2;
        public TextView city;
        public TextView state;
        public TextView zip;

        private AddressCard currentCard;

        public CardViewHolder(View v) {
            super(v);
            cardView = (CardView)v.findViewById(R.id.address_card_view);
            contactName = (TextView)v.findViewById(R.id.contact_name);
            address = (TextView)v.findViewById(R.id.address);
            address2 = (TextView)v.findViewById(R.id.address2);
            city = (TextView)v.findViewById(R.id.city);
            state = (TextView)v.findViewById(R.id.state);
            zip = (TextView)v.findViewById(R.id.zip);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Editing contact info for " + currentCard.getContactName(), Toast.LENGTH_SHORT).show();
                    editAddress(v, currentCard);
                }
            });
        }
    }

    public AddressCardRecyclerAdapter(List<AddressCard> cards) {
        this.cards = cards;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_card_view, parent, false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CardViewHolder cvh, int position) {
        AddressCard currentCard = cards.get(position);

        String add2 = currentCard.getAddress2();
        if (add2 == null)
            cvh.address2.setVisibility(View.GONE);

        cvh.contactName.setText(currentCard.getContactName());
        cvh.address.setText(currentCard.getAddress());
        cvh.address2.setText(add2);
        cvh.city.setText(currentCard.getCity() + ", ");
        cvh.state.setText(currentCard.getState() + " ");
        cvh.zip.setText(currentCard.getZip());

        cvh.currentCard = currentCard;
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    private static void editAddress(View v, AddressCard currentCard) {
        FragmentTransaction ft = ((Activity)v.getContext()).getFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        DialogFragment editAddress = AddressEditDialogFragment.newInstance(currentCard, (AddressList)v.getContext()); //really bad... how else to do it?
        editAddress.show(ft, "editAddress");
    }

}

