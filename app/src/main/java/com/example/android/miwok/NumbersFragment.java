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
public class NumbersFragment extends Fragment {
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
        words.add(new word("lutti","One",R.drawable.number_one,R.raw.number_one));
        words.add(new word("otiiko","Two",R.drawable.number_two,R.raw.number_two));
        words.add(new word("tolookosu","Three",R.drawable.number_three,R.raw.number_three));
        words.add(new word("oyyisa","Four",R.drawable.number_four,R.raw.number_four));
        words.add(new word("massokka","Five",R.drawable.number_five,R.raw.number_five));
        words.add(new word("temmokka","Six",R.drawable.number_six,R.raw.number_six));
        words.add(new word("kenekaku","Seven",R.drawable.number_seven,R.raw.number_seven));
        words.add(new word("kawinta","Eight",R.drawable.number_eight,R.raw.number_eight));
        words.add(new word("wo’e","Nine",R.drawable.number_nine,R.raw.number_nine));
        words.add(new word("na’aacha","Ten",R.drawable.number_ten,R.raw.number_ten));
        WordAdapter adapter = new WordAdapter((AppCompatActivity) getActivity(),R.layout.list_item, words,R.color.category_numbers);

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
                    mMediaPlayer=MediaPlayer.create(getActivity(),word.getaudioid());
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
