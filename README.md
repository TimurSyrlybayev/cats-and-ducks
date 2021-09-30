# Cats and Ducks App
This is an app for displaying cats and ducks images.

## Usage

You can click on one of the buttons ("Show cat" or "Show duck") of your choice, and respective image will appear.
If you want to get another image, click the button once again.

If you liked some image very much and want to add it to your Favourites, just double tap on that image.

On the right side of the toolbar you can find button leading to Favourites screen:

<img width="540" alt="Screenshot_1633005396" src="https://user-images.githubusercontent.com/66127317/135456690-af1f4ead-fb02-459b-8fde-85cdb686db39.png">

By clicking this button you will be redirected to the screen where your favourite images were saved:

<img width="188" alt="Screenshot_1633005920" src="https://user-images.githubusercontent.com/66127317/135457790-6c335f9b-58dd-4d62-bb25-6165869072bd.png">

On this screen you can find list of saved images added by you earlier from the main screen and "Clear all" button at the right bottom. If you click on this button all images will be deleted.

On each particular image you can see "Like" icon and time-and-date when image was saved by you:

<img width="177" alt="Screenshot_1633005920" src="https://user-images.githubusercontent.com/66127317/135458575-811176b3-bfa8-4161-a208-0355f23fa68e.png">

If you'd like to remove the image from the list, just "unlike" it (tap on the "Like" icon) and this image will be completely deleted (from the screen and from the database of your favourite images).

If you'd like to see the image in full size, tap on it. Then Preview page will open displaying only this image for you:

![Screenshot_1633006677](https://user-images.githubusercontent.com/66127317/135459470-f3ccad05-eb5c-47f3-af8b-010c1ce02ed2.png)

## Applied technologies and services

- Architecture: Single Activity with Model-View-ViewModel pattern;
- Images are received from two APIs: [cats API](https://thatcopy.pw/catAPI/rest) and [ducks API](https://random-d.uk/api/random);
- All network work is made using Retrofit and Picasso;
- Dependency injections made with Dagger 2;
- All database operations fulfilled via Room;
- Favourite screen list made with RecyclerView;
- LiveData and Observer pattern allows to renew UI "on the fly" as soon as something changed in database or network services;
