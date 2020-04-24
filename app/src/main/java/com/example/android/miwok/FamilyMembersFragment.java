package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
public class FamilyMembersFragment extends Fragment {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudiomanager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if( focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer(mMediaPlayer);
            }
        }
    };
    private MediaPlayer.OnCompletionListener onCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer(mp);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        mAudiomanager= (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<word> words=new ArrayList<word>();
        words.add(new word("әpә","father",R.drawable.family_father,R.raw.family_father));
        words.add(new word("әṭa","mother",R.drawable.family_mother,R.raw.family_mother));
        words.add(new word("angsi","son",R.drawable.family_son,R.raw.family_son));
        words.add(new word("tune","daughter",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new word("taachi","older brother",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new word("chalitti","younger brother",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new word("teṭe","older sister",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new word("kolliti","younger sister",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new word("ama","grandmother",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new word("paapa","grandfather",R.drawable.family_grandfather,R.raw.family_grandfather));
        WordAdapter adapter = new WordAdapter((AppCompatActivity) getActivity(),R.layout.list_item, words,R.color.category_family);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer(mMediaPlayer);
                word word=words.get(position);
                int result = mAudiomanager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    releaseMediaPlayer(mMediaPlayer);
                    mMediaPlayer= MediaPlayer.create(getActivity(),word.getaudioid());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });
        return rootView;
    }
    private void releaseMediaPlayer(MediaPlayer mp) {
        if(mp!=null){
            mp.release();
        }
        mp=null;
        mAudiomanager.abandonAudioFocus(mOnAudioFocusChangeListener);
    }

    @Override
    public void onStop() {
        releaseMediaPlayer(mMediaPlayer);
        super.onStop();
    }
}
