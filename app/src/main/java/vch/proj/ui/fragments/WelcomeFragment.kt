package vch.proj.ui.fragments

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import vch.proj.R
import vch.proj.helpers.Helper.Companion.l
import vch.proj.ui.activities.NotesActivity
import vch.proj.ui.activities.WelcomeActivity

class WelcomeFragment : Fragment() {
    private val PORTRAIT_ORIENTATION = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.welcome_activity, container, false)

        view.setOnClickListener {
            val intent = NotesActivity.newIntent(requireContext())
            startActivity(intent)
            activity?.finish()
        }

        //set correct background image
        val linearLayout: LinearLayout = view.findViewById(R.id.welcome_liner_layout)
        var background: Drawable?

        if (PORTRAIT_ORIENTATION == resources.configuration.orientation) {
            background = requireContext().getDrawable(R.drawable.welcome_background_portrait)
        } else {
            background = requireContext().getDrawable(R.drawable.welcome_background_landscape)
        }

        Glide.with(view.context)
            .load(background)
            .into(object : CustomTarget<Drawable>() {
                override fun onLoadCleared(p0: Drawable?) {}

                override fun onResourceReady(p0: Drawable, p1: Transition<in Drawable>?) {
                    linearLayout.background = background
                }
            })

        return view
    }
}