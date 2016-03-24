package me.darkeet.android.demo.volley;

import android.os.Build;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;

import java.util.ArrayList;

import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.error.VolleyError;

/**
 * A class that wraps up remote image loading requests using the Volley library combined with a
 * memory cache. An single instance of this class should be created once when your Activity or
 * Fragment is created, then use {@link #get(String, android.widget.ImageView)} or one of
 * the variations to queue the image to be fetched and loaded from the network. Loading images
 * in a {@link android.widget.ListView} or {@link android.widget.GridView} is also supported but
 * you must store the {@link com.android.volley.Request} in your ViewHolder type class and pass it
 * into loadImage to ensure the request is canceled as views are recycled.
 */
public class ImageLoader extends com.android.volley.toolbox.ImageLoader {
    private static final ColorDrawable transparentDrawable = new ColorDrawable(
            android.R.color.transparent);

    private static final int ANIMATION_FADE_IN_TIME = 250;
    private static final int HALF_FADE_IN_TIME = ANIMATION_FADE_IN_TIME / 2;

    private Resources mResources;
    private ArrayList<Drawable> mPlaceHolderDrawables;
    private boolean mFadeInImage = true;
    private int mMaxImageHeight = 0;
    private int mMaxImageWidth = 0;
    private RequestQueue mRequestQueue;

    /**
     * Creates an ImageLoader with Bitmap memory cache. No default placeholder image will be shown
     * while the image is being fetched and loaded.
     */
    public ImageLoader(FragmentActivity activity, RequestQueue queue) {
        super(queue, BitmapCache.getInstance(activity.getSupportFragmentManager()));
        mRequestQueue = queue;
        mResources = activity.getResources();
    }

    /**
     * Creates an ImageLoader with Bitmap memory cache and a default placeholder image while the
     * image is being fetched and loaded.
     */
    public ImageLoader(FragmentActivity activity, RequestQueue queue, int defaultPlaceHolderResId) {
        super(queue, BitmapCache.getInstance(activity.getSupportFragmentManager()));
        mRequestQueue = queue;
        mResources = activity.getResources();
        mPlaceHolderDrawables = new ArrayList<Drawable>(1);
        mPlaceHolderDrawables.add(defaultPlaceHolderResId == -1 ?
                null : mResources.getDrawable(defaultPlaceHolderResId));
    }

    /**
     * Creates an ImageLoader with Bitmap memory cache and a list of default placeholder drawables.
     */
    public ImageLoader(FragmentActivity activity, RequestQueue queue, ArrayList<Drawable> placeHolderDrawables) {
        super(queue, BitmapCache.getInstance(activity.getSupportFragmentManager()));
        mRequestQueue = queue;
        mResources = activity.getResources();
        mPlaceHolderDrawables = placeHolderDrawables;
    }

    /**
     * Starts processing requests on the {@link RequestQueue}.
     */
    public void startProcessingQueue() {
        mRequestQueue.start();
    }

    /**
     * Stops processing requests on the {@link RequestQueue}.
     */
    public void stopProcessingQueue() {
        mRequestQueue.stop();
    }

    public ImageLoader setFadeInImage(boolean fadeInImage) {
        mFadeInImage = fadeInImage;
        return this;
    }

    public ImageLoader setMaxImageSize(int maxImageWidth, int maxImageHeight) {
        mMaxImageWidth = maxImageWidth;
        mMaxImageHeight = maxImageHeight;
        return this;
    }

    public ImageLoader setMaxImageSize(int maxImageSize) {
        return setMaxImageSize(maxImageSize, maxImageSize);
    }

    public ImageContainer get(String requestUrl, ImageView imageView) {
        return get(requestUrl, imageView, 0);
    }

    public ImageContainer get(String requestUrl, ImageView imageView, int placeHolderIndex) {
        return get(requestUrl, imageView, mPlaceHolderDrawables.get(placeHolderIndex),
                mMaxImageWidth, mMaxImageHeight);
    }

    public ImageContainer get(String requestUrl, ImageView imageView, Drawable placeHolder) {
        return get(requestUrl, imageView, placeHolder, mMaxImageWidth, mMaxImageHeight);
    }

    public ImageContainer get(String requestUrl, ImageView imageView, Drawable placeHolder,
                              int maxWidth, int maxHeight) {

        // Find any old image load request pending on this ImageView (in case this view was
        // recycled)
        ImageContainer imageContainer = imageView.getTag() != null &&
                imageView.getTag() instanceof ImageContainer ?
                (ImageContainer) imageView.getTag() : null;

        // Find image url from prior request
        String recycledImageUrl = imageContainer != null ? imageContainer.getRequestUrl() : null;

        // If the new requestUrl is null or the new requestUrl is different to the previous
        // recycled requestUrl
        if (requestUrl == null || !requestUrl.equals(recycledImageUrl)) {
            if (imageContainer != null) {
                // Cancel previous image request
                imageContainer.cancelRequest();
                imageView.setTag(null);
            }
            if (requestUrl != null) {
                // Queue new request to fetch image
                imageContainer = get(requestUrl,
                        getImageListener(mResources, imageView, placeHolder, mFadeInImage),
                        maxWidth, maxHeight);
                // Store request in ImageView tag
                imageView.setTag(imageContainer);
            } else {
                imageView.setImageDrawable(placeHolder);
                imageView.setTag(null);
            }
        }

        return imageContainer;
    }

    private static ImageListener getImageListener(final Resources resources,
                                                  final ImageView imageView, final Drawable placeHolder, final boolean fadeInImage) {
        return new ImageListener() {
            @Override
            public void onSuccess(ImageContainer response, boolean isImmediate) {
                imageView.setTag(null);
                if (response.getBitmap() != null) {
                    setImageBitmap(imageView, response.getBitmap(), resources,
                            fadeInImage && !isImmediate);
                } else {
                    imageView.setImageDrawable(placeHolder);
                }
            }

            @Override
            public void onError(VolleyError error) {
            }
        };
    }

    /**
     * Sets a {@link android.graphics.Bitmap} to an {@link android.widget.ImageView} using a
     * fade-in animation. If there is a {@link android.graphics.drawable.Drawable} already set on
     * the ImageView then use that as the image to fade from. Otherwise fade in from a transparent
     * Drawable.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private static void setImageBitmap(final ImageView imageView, final Bitmap bitmap,
                                       Resources resources, boolean fadeIn) {

        // If we're fading in and on HC MR1+
        if (fadeIn && (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB_MR1)) {
            // Use ViewPropertyAnimator to run a simple fade in + fade out animation to update the
            // ImageView
            imageView.animate()
                    .scaleY(0.95f)
                    .scaleX(0.95f)
                    .alpha(0f)
                    .setDuration(imageView.getDrawable() == null ? 0 : HALF_FADE_IN_TIME)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            imageView.setImageBitmap(bitmap);
                            imageView.animate()
                                    .alpha(1f)
                                    .scaleY(1f)
                                    .scaleX(1f)
                                    .setDuration(HALF_FADE_IN_TIME)
                                    .setListener(null);
                        }
                    });
        } else if (fadeIn) {
            // Otherwise use a TransitionDrawable to fade in
            Drawable initialDrawable;
            if (imageView.getDrawable() != null) {
                initialDrawable = imageView.getDrawable();
            } else {
                initialDrawable = transparentDrawable;
            }
            BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, bitmap);
            // Use TransitionDrawable to fade in
            final TransitionDrawable td =
                    new TransitionDrawable(new Drawable[]{
                            initialDrawable,
                            bitmapDrawable
                    });
            imageView.setImageDrawable(td);
            td.startTransition(ANIMATION_FADE_IN_TIME);
        } else {
            // No fade in, just set bitmap directly
            imageView.setImageBitmap(bitmap);
        }
    }

    /**
     * Interface an activity can implement to provide an ImageLoader to its children fragments.
     */
    public interface ImageLoaderProvider {
        public ImageLoader getImageLoaderInstance();
    }

}
