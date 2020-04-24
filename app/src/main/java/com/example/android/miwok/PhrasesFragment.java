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
public class PhrasesFragment extends Fragment {
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
        words.add(new word("minto wuksus","Where are you going?",R.raw.phrase_where_are_you_going));
        words.add(new word("tinnә oyaase'nә","What is your name?",R.raw.phrase_what_is_your_name));
        words.add(new word("oyaaset...","My name is...",R.raw.phrase_my_name_is));
        words.add(new word("michәksәs?","How are you feeling?",R.raw.phrase_how_are_you_feeling));
        words.add(new word("kuchi achit","I’m feeling good.",R.raw.phrase_im_feeling_good));
        words.add(new word("әәnәs'aa?","Are you coming?",R.raw.phrase_are_you_coming));
        words.add(new word("hәә’ әәnәm","Yes, I’m coming.",R.raw.phrase_yes_im_coming));
        words.add(new word("әәnәm","I’m coming.",R.raw.phrase_im_coming));
        words.add(new word("yoowutis","Let's go.",R.raw.phrase_lets_go));
        words.add(new word("әnni'nem","Come here",R.raw.phrase_come_here));
        WordAdapter adapter = new WordAdapter((AppCompatActivity) getActivity(),R.layout.list_item, words,R.color.category_phrases);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer(mMediaPlayer);
                word word=words.get(position);
                int result = mAudiomanager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
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
