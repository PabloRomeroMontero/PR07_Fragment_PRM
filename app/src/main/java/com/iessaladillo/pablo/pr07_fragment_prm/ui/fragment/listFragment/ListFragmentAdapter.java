package com.iessaladillo.pablo.pr07_fragment_prm.ui.fragment.listFragment;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.iessaladillo.pablo.pr07_fragment_prm.R;
import com.iessaladillo.pablo.pr07_fragment_prm.data.local.model.User;
import com.iessaladillo.pablo.pr07_fragment_prm.databinding.FragmentItemBinding;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ListFragmentAdapter extends ListAdapter<User,ListFragmentAdapter.ViewHolder> {
        private final OnUserListener onUserListener;

        public ListFragmentAdapter( OnUserListener onUserListener){
            super(new DiffUtil.ItemCallback<User>() {
                @Override
                public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                    return TextUtils.equals(oldItem.getName(), newItem.getName()) &&
                            TextUtils.equals(oldItem.getAddress(), newItem.getAddress()) &&
                            TextUtils.equals(oldItem.getWeb(), newItem.getWeb()) &&
                            TextUtils.equals(oldItem.getEmail(), newItem.getEmail()) &&
                            oldItem.getNumber() == newItem.getNumber();
                }
            });
            this.onUserListener = onUserListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.fragment_item, parent,false),onUserListener);
//        return new ViewHolder(LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.activity_main_item,parent,false), onUserListener);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(getItem(position));
        }

        public long getItemId(int position){
            return getItem(position).getId();
        }

        static class ViewHolder extends RecyclerView.ViewHolder{
            private FragmentItemBinding b;

            public ViewHolder(@NonNull FragmentItemBinding b, OnUserListener onUserListener) {
                super(b.getRoot());
                this.b = b;
                this.b.bttEdit.setOnClickListener(v -> onUserListener.onItemClickEdit(getAdapterPosition()));
                this.b.bttDelete.setOnClickListener(v -> onUserListener.onItemClickDelete(getAdapterPosition()));
            }

            public void bind(User user) {
                b.cvName.setText(user.getName());
                b.cvEmail.setText(user.getEmail());
                b.cvAvatar.setImageResource(user.getAvatar().getImageResId());
                b.cvPhonenumber.setText(String.valueOf(user.getNumber()));
            }
        }
        @Override
        public User getItem(int position) {
            return super.getItem(position);
        }


    }

