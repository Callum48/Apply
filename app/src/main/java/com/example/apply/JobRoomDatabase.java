package com.example.apply;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Creates the database
 * Every other interaction with the db is done through the JobViewModel
 */

@Database(entities = {Job.class}, version = 3, exportSchema = false)
public abstract class JobRoomDatabase extends RoomDatabase {

    // DAO (database access object) that works with database
    public abstract JobDao jobDao();

    private static JobRoomDatabase INSTANCE;

    // Create a single instance of the database
    public static JobRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (JobRoomDatabase.class){
                if(INSTANCE  == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            JobRoomDatabase.class, "job_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{

        private final JobDao mDao;
        String[] titles = {"Pizza Delivery Driver", "Cafe Assistant/Barista", "Experienced Kitchen Hands wanted!", "Casual Event Staff", "Casual Vacancies - Pak'nSAVE Mill Street"};
        String[] employers = {"HELL Pizza", "Coffee Culture", "The Recruitment Network", "ASB Showgrounds", "Gladstone Retail ltd"};
        String[] emails = {"hell.pizza@gmail.com", "owner@coffeeculture.com", "hospitality@trn.com", "events@asbshowgrounds.co.nz", "millstreet@paknsave.co.nz"};
        String[] summaries = {"Our team is looking to expand and are welcoming applications for part time roles.",
                "We’re looking for well-presented and dynamic people with a passion for great coffee and service to be part of our new team.",
                "This is a great opportunity for a versatile kitchen hand to be part of a great team!",
                "We are currently looking for passionate and motivated casual staff for event work with flexible hours",
                "Come and work with our close-knit team at New Zealand's favourite supermarket!"};
        String[] descriptions = {"Our team is looking to expand and are welcoming applications for the below part time roles.\n\n" +
                "- Delivering pizzas and other products to customers.\n" +
                "- Providing quality customer service at the door.\n" +
                "- Dealing with customer concerns.\n" +
                "- Driving safely at all times.\n" +
                "- Assisting with customer service in store.\n" +
                "- Assisting with food preparation.\n" +
                "- General cleaning duties\n\n" +
                "No experience necessary, just need to have a clean licence and be available for Friday/Saturday nights and our peak times (generally 5pm - 8pm)\n\n" +
                "Applicants for this position should have NZ residency or a valid NZ work visa, NZ driving licence and a car.\n\n" +
                "Please note: if you don't have access to a car there are some options so if you're keen, apply now!",

                "Opening Soon. Hiring Now.\n\n" +
                "Coffee Culture is currently underway with construction of a bright and beautiful new store in Riccarton, Christchurch and we’re looking for well-presented and dynamic people with a passion for great coffee and service to be part of our new team. We’re opening soon and will begin training in April.\n\n" +
                "You'll need to be a quick learner, hard-working and cool under pressure. Flexibility to work on a weekend roster that includes nights is needed. Previous industry experience will be helpful but we do offer great training, so if you’re good with people and love coffee we’d love to have you work with us.\n\n" +
                "Our stores offer a fun, friendly and fast paced work environment, structured hours and development opportunities for those that show a passion and commitment to what we do.\n\n" +
                "If this sounds like you, we’d love to chat. Send your CV and a few words about yourself to Annabelle Sare or drop your CV in to our Addington store and say hi.",

                "In this casual position you will assist Chefs and Cooks in providing a high standard of hot and cold food preparation and production. You will be responsible for sanitation and safe food handling as well as providing our customers with a positive catering and hospitality experience.\n\n" +
                "To be successful in this role you will demonstrate:\n\n" +
                "- Understanding of Food Safety practices\n" +
                "- Food handling & Hygiene Certificate an advantage\n" +
                "- Previous food preparation experience in a commercial kitchen\n" +
                "- Ability to work in a fast-paced, hands-on environment\n" +
                "- Impeccable grooming and maintain good personal hygiene standards\n" +
                "- Strong communication skills\n" +
                "- A positive can do attitude\n\n" +
                "If you are looking for an opportunity to join a team that is committed to fresh food and great people, we want to hear from you!\n" +
                "Join TRN Now and you will never look back! What are you waiting for? We could have you working tomorrow!",

                "We are currently looking for passionate and motivated casual staff for event work with flexible hours. A successful candidate will have a great attitude and willingness to work as and where needed in multiple different capacities and be adaptable to a changing system that meets our clients’ needs. They will also be able to work well in a team and in a fast-paced environment.\n\n" +
                "Applicants for this position should have NZ residency or a valid NZ work visa.\n\n" +
                "If you’re interested, please email through your CV to events@asbshowgrounds.co.nz",

                "We are currently interviewing for a number of casual retail staff to help us meet shortfalls in our store over the coming months.\n\n" +
                "As a casual staff member you will be assigned to one department, but as the needs of the business change you may get the opportunity to work in multiple areas around the store.\n\n" +
                "Areas we are looking to fill:\n\n" +
                "- Checkout operators\n" +
                "- Grocery team members\n" +
                "- Trolley assistants\n" +
                "- Fresh foods departments, including chilled and frozens, service deli, seafood… etc\n\n" +
                "What we can offer you:\n\n" +
                "- Employment in a stable business during these uncertain times\n" +
                "- The flexibility of a casual position\n" +
                "- On the job training\n" +
                "- A positive and supportive workplace committed to your health and wellbeing\n\n" +
                "We are looking for reliable people with positive energy, who are keen to help out when needed and are proud to be able to serve their community.\n\n" +
                "Applicants for the Checkout Supervisor roles will need to have related previous experience. All other roles are entry level and you will receive the necessary training to get you started.\n\n" +
                "Please submit with your application your CV and Cover Letter.\n\n" +
                "You must have the right to work in New Zealand to apply for these roles."};
        String[] locations = {"Wellington", "Canterbury", "Northland", "Northland", "Waikato"};
        int[] hours = {7, 8, 10, 6, 8};

        PopulateDbAsync(JobRoomDatabase db){
            mDao = db.jobDao();
        }

        @Override
        protected Void doInBackground(final Void... params){
            // if there aren't any jobs in db, then create the initial list of jobs
            if(mDao.getAnyJob().length < 1){
                for(int i = 0; i < titles.length; i++){
                    Job job = new Job(titles[i], employers[i], emails[i], summaries[i], descriptions[i], locations[i], hours[i]);
                    mDao.insert(job);
                }
            }
            return null;
        }
    }
}
