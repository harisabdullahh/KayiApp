package com.example.kayiapp;

import static android.provider.CalendarContract.CalendarCache.URI;

import static java.lang.Integer.parseInt;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Call;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.util.MimeTypes;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    String title;

    String fruitList[] = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60","61","62","63","64","65","66","67","68","69","70","71","72","73","74","75","76","77","78","79","80","81","82","83","84","85","86","87","88","89","90","91","92","93","94","95","96","97","98","99","100","101","102","103","104","105","106","107","108","109","110","111","112","113","114","115","116","117","118","119","120","121","122","123","124","125","126","127","128","129","130","131","132","133","134","135","136","137","138","139","140","141","142","143","144","145","146","147","148","149","150"};
    int fruitImages [] = {R.drawable.ertugrul_img1};

    Menu menuObj = new Menu();

    public String episodeDescription[] = {
            "The poverty-stricken Kayı search for a new home. Meanwhile, Ertuğrul saves a family of Selçuk royals from the Templars and brings them to the camp. Matters are therefore worsened for the Kayı, many of whom oppose the new arrivals. Kara Toygar threatens the Kayi camp when Suleyman Shah refuses to turn over the prisoners. Titus learns the excruciating truth about his brother. In the search for a new camp, Suleyman sends Ertugrul to Aleppo and fuels fraternal jealously. Kurdogu makes a deal with Kara Toygar.\n",
            "On the way to Aleppo, a dangerous enemy crosses paths with Ertugrul. Halime and her family come under attack. Fate hands Titus a golden chance for revenge, but the timing is not right. Selcan conspires with Gokce to boost their status in the family. A premonition saves Ertugrul's life as Titus raises the stakes against him. Ustadi Azam turns up the heat on his rivals.\n",
            "Suleyman's prayers are answered, but Gundodgu's life hangs by a thread. Kurdoglu puts another plan into action. A mysterious messenger puts a damper on the Kayi tribe's festivities. Suleyman presses Halime's father about his true identity. Sensing imminent danger, Sehzade Numan asks Suleyman for a favor. Suleyman shows Kara Toygar that he has the upper hand - but not for long.\n",
            "The prisoner exchange goes awry. At the camp, Hayde stands up to Kurdoglu, who exploits another opportunity to strike against Suleyman. Kurdoglu puts his plan in motion, but Ertugrul is one step ahead of him. Selcan tries to turn the tables in her favor. Kurdoglu intervenes on behalf of Kara Toygar. Selcan's maneuvering pays off, but Gundogdu spoils everything.\n",
            "On the way to Aleppo, Ertugrul and Halime walk into lion's den. Gundogdu and Kurdoglu have a heated face-off. Ustadi Azam is determined to eliminate anyone who stands in the way of his campaign. Suleyman's greatest threat is close by. Selcan tries to kill two birds with one stone, but her ambition becomes her downfall. With Halime in critical condition, Ertugrul's group splits up.\n",
            "Ustadi Azam uses Ibn Arabi to tighten the screws on Giovanni. An informant pulls the wool over Sahabettin's eyes. Sahabettin falls deeper into love's trap. The Knights Templar's lethal weapon strikes the Kayi tribe on the eve of their migration. Sahabettin is key to Ertugrul's plan, but El Aziz stands in the way. Turgut and Yigit are still missing, but Ertugrul has a more immediate problem.\n",
            "After narrowly escaping a tragic fate, Ertugrul makes a radical plan as time runs out for Sahabettin. With El Aziz unwavering in his decision, Sahabettin prepares to embrace his fate, but a hooded stranger makes a surprise appreance. Izadora takes Yigit under her wing, Nasir controls El Aziz as a puppet leader and orders a thorough manhunt to finish what he started.\n",
            "Ertugrul cautions Numan, who puts himself in the firing line at El Aziz's palace. A visitor gives Turgut hope. Deli Demir heads for Aleppo and cannot believe what he sees upon arrival. Ertugrul puts his life on the line to set a trap for Nasir. Titus brushes up against a sworn enemy from the past. During a confrontation with Nasir, Ertugrul faces a difficult choice.\n",
            "Leyla helps Ertugrul at a critical moment, but her absence draws attention. Seeking answers about her family, Izadora makes a deal with Turgut. Ertugrul makes a narrow escape. Nasir has an elaborate plan for Halime, who struggles to make El Aziz see the truth. Aykiz takes matters into her own hands and pays Effelya a visit. Ustadi Azam goes after a mole on his own turf.\n",
            "Gundogdu and Selcan come to blows over her ambitions. Ertugrul brings pressure to bear on Effelya, but the tribe needs her alive. To infiltrate the Knights Templar's base, Ertugrul deliberately leaks information. Numan's decision deals a major blow to Halime. Ertugrul captures an important prisoner on the way. Ustadi Azam grooms Yigit for the final stage of his plan.\n",
            "A message arrives at the Kayi camp and Selcan decides it is time to settle accounts with Suleyman. Marcus falls for the Kayi tribe ruse. Titus wreaks havoc on the camp. A surprise visitor knocks on Afsin's door, and Numan leaves Ertugrul no choice but to walk away from Halime. As Titus's plan spirals out of control, he and Kurdoglu look for a way out. Sahabettin watches them closely.\n",
            "The Knights Templar's position in Aleppo depends on Eftelya, who tries to turn Ertugrul against Halime. Eftelya decides her own fate. A showdown between Ertugrul and Titus gets interrupted and Ertugrul questions Kara Toygar's true motives. Nasir oversteps his bounds with El Aziz and Halime seizes her chance to appeal on behalf of the Kayi tribe.\n",
            "In pitting herself against Selcan, Aykiz plays with fire. Ertugrul rushes to head off the diplomatic mess created by Nasir. Selcan falls out with Hayme's favor. Ertugrul walks into an important meeting and exposes a conspiracy against his tribe. El Aziz hands down harsh sentence to Layla and Nanny. Ertugrul and Gundogdu return home to a slew of shocking news.\n",
            "News about Halime sends Ertugrual back on the road. Claudius accepts a mission that puts Ibn Arabi in harm's way. Kurdoglu tries to turn Suleyman and Gundogdu against Ertugrul, who negotiates with El Aziz at knifepoint. Ustadi Azam loses his grip on Aleppo. At the camp, public opinion is not in Suleyman's favor and Hayme cracks down on Selcan.\n",
            "Claudius' mission hits a snag. Hayme discovers the Selcan has a personal vendetta, but Gundogdu stands by Selcan. Gundogdu unwittingly becomes a part of Kurdoglu's scheme. Hayme gives Halime and Gokce a dilemma. Gundogdu squares off with Ertugrul. Selcan stirs the pot at the family meal, where an unexpected guest arrives with distressing news for Halime.\n",
            "Cardinal Thomas and Titus carry out their plan with Turgut, whose decisions spell disaster for Ertugrul. Suleyman meets with the Seljuks, but Ertugurul's return throws the camp into disarray. Halime refuses the Seljuk sultan's offer. With their greatest obstacle out of the way Ustadi Azam and Kurdoglu advance on their target, but Gundogdu tests Kurdoglu's loyalty.\n",
            "A visitor brings news of Turgut to the Kayi camp and puts Kurdoglu in an unfavorable position. A murder plot unravels. Kurdoglu incurs Selcan's wrath. Izadora helps Suleyman connect the dots and Eftelya gets her hands on an important letter. Ertugrul and Suleyman put Kurdoglu on the stand, but Gundogdu steps in. Thomas tests Ustadi Azam's patience.\n",
            "Turgut is not out of the woods yet. Selcan pulls strings to influence Gundogdu and Hayme loses control of the situation. Gokce overhears Selcan's secret plan, and Suleyman invites a contest for power that threatens to pit his sons against each other. Gokce sees Selcan's true colors and confronts her. Gundugodu plays into Kurdoglu's hands, but someone more powerful has a hidden plan.\n",
            "Selcan loses favor with Gundogdu as her ambitions get the best of her. Kurdoglu unleashes another weapon to sow chaos in the camp. While Suleyman gives him the benefit of the doubt, Kurdoglu misleads Suleyman's men. Yigit's life is in the enemy hands. After losing their only witness, Ertugrul and his men are back to square one on the hunt for the traitor, but Izadora gives them another lead.\n",
            "Kurdoglu takes advantage of Ertugrul's absence. When the Knights Templar outnumber Ertugrul, his wit is the only thing that can save him. Kurdoglu tries to force Selcan's hand and makes Turgut his right-hand man. Ertugrul has valuable information that Ustadi Azam needs. Struggling to keep order among his subjects, Kurdoglu hands down Suleyman's sentence and sets a dangerous trap for him and Hayme.\n",
            "Selcan repents and decides to mend fences with those she has betrayed. Ertugrul meets an unlikely ally. Ertugrul prepares to settle accounts with Kurdoglu who is suspicious of Ayhan's loyalties. The Kayi tribe's fate depends on Turgut. The Knights Templar's plan comes apart at the seems and Izadora pays a price. Claudius causes a rift between Thomas and Ustadi Azam.\n",
            "A confrontation between Ertugrul and Kurdoglu ends in bloodshed. Within Ustadi Azain in grave condition, Thomas gains the upper hand. Kurdoglu's judgement day arrives, but Thomas still has one advantage over the Kayi tribe. Ibn Arabi convinces Selcan to change her ways. Titus is determined to eradicate the Kayi tribe. Claudius brings urgent news to Ertugrul who plans an offensive campaign into enemy territory.\n",
            "After Deli Demir and Afsin Bey find a way to infiltrate the Knights Templar, the Kayi tribe readies for battle. Ertugrul's army catches the Knights Templar off guard. In a tight spot, Titus suspects there's an informant from within. Under Titus's watch, Omer plays a risky double game. Selcan's sudden realization changes her fate.\n",
            "Omer pays a heavy price for his betrayal. Yigit helps to lead Ertugrul and his army on the road to victory. Turgut gets his sweet revenge. Within Titus still a threat to the Kayi army. Ertugruls men set out on a manhunt. Gundogdu responds unexpectedly to Selcan's big news. At the Kayi camp, danger still lingers in the shadows.\n",
            "Selcan atones for her crimes and risks her life for Gundogdu, who confronts a dangerous enemy from the past. Ibn Arabi leaves Ertugrul with a valuable relic. A perilous journey lies ahead for Ertugrul and Suleyman. As Ertugrul consolidates power across Anatolia, Hayme fears that an ominous premonition may come true.\n",
            "As the Kayi tribe struggles with its sudden loss of Süleyman Şah, Hayme calls a meeting to form a strategy against new threats.\n",
            "The Mongols, under Noyan's command, attack the Kayı tribe. Ertuğrul is captured by Tangut, Noyan's right-hand-man, while the rest of the tribe seeks refuge in the Dodurga tribe. The Dodurga tribe is ruled by Korkut, Hayme's brother.\n",
            "Aykız is buried after being burnt in the Mongol attack. Gündoğdu and Korkut's son, Tuğtekin set out to find Ertuğrul, but return with the news of Ertuğrul's death. This false news turns into great pain for the Kayı tribe. When Halime heard the news of her husband's death, she was near to losing her child. Tuğtekin, the ambitious nephew of Hayme Ana, takes command of the alps of the Dodurga and the Kayı causing great troubles for all the alps. Meanwhile, Ertuğrul is tortured by Mongols and he struggles to escape from them.\n",
            "The Kayı and Dodurga people ally with the Selçuk Sultan Alaeddin Keykubat. Noyan torments Ertuğrul to join his ranks. Ertuğrul refuses to do so, causing Noyan's brutal reaction by piercing his hand with a nail to make it almost impossible to hold a sword. However, Ertuğrul manages to escape by confronting Tangut and the Mongol soldiers.\n",
            "Ertuğrul is determined to go to his tribe as soon as possible and start preparations to take measures against the Mongols. The sudden appearance of Ertuğrul with his alps creates great excitement and happiness for his lovers. It also causes a great shock for Tuğtekin and Korkut's devious wife, Aytolun, Tuğtekin's stepmother. Ertuğrul is determined to keep the head of alps duty that his father gave him. Due to this decision, he faces Korkut Bey, his mother, Gündoğdu, and, Tuğtekin, also affecting the alps of both tribes.\n",
            "Ertuğrul fails to become the head of alps. Korkut, Hayme and Gündoğdu mourn over this decision. However, they believed that Ertuğrul just wanted revenge. The injury of Ertuğrul's hand was a factor in Hayme's decision. The removal of Ertuğrul from his duties leaves the Kayı alps in great astonishment.\n",
            "Ertuğrul comes face to face with his mother Hayme and his brother Gündoğdu due to the strife of Aytolun, Koçabaş, the Mongol traitor, and Tuğtekin. Everyone wants to spend a peaceful and warm winter to meet their needs while Ertuğrul starts the preparations for a war against the Mongols with the help of Geyikli and his alps Doğan and Turgut.\n",
            "In order to break Ertuğrul's will to fight, both the traitors inside and the enemy Mongols outside want to keep him under pressure. Ertuğrul also confronts his mother Hayme and his elder brother Gündoğdu causing breaks and splits in the family. Ertuğrul decides to continue his struggle alone, without any hesitation despite all the negative conditions. Hamza Alp joins the Mongols, seeking revenge on Gündoğdu who blamed him for allying with them in the first place, he helps them recruit more Turk warriors. Seeing this, Abdurrahman is imprisoned with the death penalty for being a traitor in the tribe as he stayed around with Hamza.\n",
            "Neither Gündoğdu, Hayme or Ertuğrul agree to the execution of Abdurrahman, who was entrusted by Ertuğrul's father Süleyman Şah. However, Gündoğdu and Hayme suggested to dismiss him from his duties. In the meanwhile, Ertuğrul plans to free him and succeeds in rescuing Abdurrahman. Knowing this, Hayme exiles Ertuğrul from the camp to prevent any further unrest.\n",
            "Hayme is caught between her principal obligations and her maternal feelings following the exile of Ertuğrul, she regrets that she exiled Ertuğrul with his injured hand. A Persian merchant Efrasiyab comes to the tribe to trade but is secretly working for Mongols. İbn-i Arabi appears in Ertuğrul dream and orders him to open the box he gave back in season 1. Ertuğrul sends Abdurrahman to the Mongols to spy for him.\n",
            "Aytolun and Tuğtekin are pleased by Hayme's exile of Ertuğrul. Aytolun wants to increase her effectiveness in the camp by marrying Tuğtekin to Gökçe. Seeing all this, Selcan goes over her. Meanwhile, Goncagül, Aytolun's niece, and Ertokuş Bey from Konya come to the tribe. Ertokuş meets with Ertuğrul and tells him about his long lost brother, Sungurtekin, which Kocabaş listens to and reveals to Noyan.\n",
            "Gümüştekin, Aytolun's brother, comes from Konya with Ertuğrul's death warrant further increasing the situation. Hayme Ana and Gündoğdu realize that they have made a big mistake by not listening to Ertuğrul from the beginning. The plans of the trio, Aytolun, Gümüştekin, and Goncagül were to kill Korkut and his son to make Gümüştekin the Turkic margarve. Ertokuş is killed by Tangut.\n",
            "Noyan starts to destroy the two tribes from the inside through his spies. Selcan and Aytolun continue with their escalated disagreements, Tuğtekin goes to find Ertuğrul due to his death warrant.\n",
            "Ertuğrul advises Tuğtekin to realize who the real traitor is. Not paying attention, Tuğtekin attacks Ertuğrul who fights and leaves. Meanwhile, Noyan attacks Tuğtekin, nearly kills him, and sends Koçabaş to the tribes to blame Ertuğrul for this. Ertuğrul comes to the camp and decapitates Koçabaş causing an increasingly suspicious behaviour towards him from everyone.\n",
            "Karabek, a Mongol spy in the Selçuks, comes to the tribe to see the death penalty of Ertuğrul. Ertuğrul is prisoned in the tribe till his punishment is decided. Halime and Deli Demir are followed by Tangut who were going to Konya to inform the Sultan about Ertuğrul. Noyan prepares his Mongols to attack the tribe.\n",
            "The court warrants a death penalty for Ertuğrul as revenge for Korkut's son's death. Tuğtekin is being treated by Artuk Bey, Ertuğrul's ally and the Dodurga medic, and İbn-i Arabi in Geyikli's cave. Mongols attack the tribe at night following Noyan's command.\n",
            "On the execution day, Ertuğrul's three alps save him. İbn-i Arabi brings Tuğtekin to the tribe who announces the betrayal of Koçabaş, decreasing the tension in the camp. It provides an environment of peace and solidarity between the two camps that hadn't existed for a long time. Tuğtekin and Gökçe's marriage preparations begin. Ertuğrul informs Abdurrahman not to return to the Mongols through Geyikli.\n",
            "The unity and solidarity between the Kayı and the Dodurga tribes disrupt Noyan's plans. Noyan injures Abdurrahman severely for deceiving them. Meanwhile, the tribe makes preparations to attack the Mongols.\n",
            "Ertuğrul goes to a caravanserai to meet Sungurtekin where Noyan attacks both of them. The alps of both the tribes attack the Mongols and kill all of them capturing Hamza. Meanwhile, Ertuğrul successfully brings his injured brother Sungurtekin back to the camp. Noyan kidnaps Selcan with the help of Erfrasiyab.\n",
            "Gümüştekin and Aytolun plan to steal the Oğuz seal from Sungurtekin to give it to Emir Saddatein Köpek. Ertuğrul goes to the Byzantine border to see the new place for the migration of his tribe with his alps. There he also fights some Byzantine soldiers.\n",
            "Ertuğrul and his alps experience new adventures and meet new people on their journey. Sungurtekin's seal is stolen in the tent of his mother, Hayme by Goncagül. Death punishment is suggested for Hamza.\n",
            "Gündoğdu rescues Selcan and captures Noyan. Hamza is martyred by Noyan. Gündoğdu brings Noyan to the tribe to execute in front of the whole tribe aimed to give strength and courage to other Turkmen tribes. On the day of execution, Noyan is saved by Emir Saadettin. Tangut controls the command of the remaining Mongols.\n",
            "Noyan's failure to be executed creates a great disappointment in the tribe. Saadettin makes other plans with Tangut. Ertuğrul and his alps set out to return to the tribe after their long journey.\n",
            "Saadetin hands over Noyan to Tangut to kill him. However, Deli Demir and Sungurtekin go after Noyan. Tangut is killed by Sungurtekin after he martyrs Deli Demir. The seal that disappeared in Hayme's tent came out of Tangut. Ertuğrul decides to settle accounts with Gümüştekin by talking to his elder brothers and Artuk Bey. Selcan tells Korkut about his first wife's death and how Aytolun killed her.\n",
            "The tension between Ertuğrul and Gümüştekin has a great impact on the camp. Aytolun and Gümüştekin decide to kill Korkut Bey, who realized their plans. Aytolun poisons him on Tuğtekin's wedding night and blames Banu Çiçek, Doğan's lover, for his death. Selcan tells Ertuğrul everything she knew and Ertuğrul orders Doğan to help Banu Çiçek escape from the prison.\n",
            "Tuğtekin wants to punish Banu Çiçek, who he thinks is his father's murderer, as soon as possible. Ertuğrul believes that Korkut Bey's death is caused by Gümüştekin and Aytolun without any evidence to prove it. After learning about Gümüştekin's intentions, Ertuğrul postpones the court through his mother. The abduction of Banu Çiçek by Doğan increases further tension in the camp.\n",
            "Tuğtekin holds Ertuğrul responsible for the escape of Banu Çiçek. Gündoğdu is assigned to capture Doğan and Banu Çiçek. Goncagül, after seeing that Selcan met Banu Çiçek, came to the camp and told her aunt what she saw. Doğan is captured by Gündoğdu Bey. On the other hand, Sadettin gathers all Turkmen beys in the caravanserai. In this meeting, Gümüştekin is appointed as the Uç Bey of the Turkmen tribes which Ertuğrul opposes.\n",
            "Banu Çiçek meets Selcan to give her the evidence. According to Ertuğrul's plan, Selcan and Banu Çiçek make Aytolun talk. Aytolun confesses to everything she did, especially the death of Duru Hanım, Korkut's first wife, and Korkut Bey himself. Tuğtekin, who was in a secret place with Ertuğrul, hears all of Aytolun's confessions. On the other hand, Gümüştekin reaches his goals and becomes the Uç Bey. He returns to the camp with great glory and everyone swears allegiance to him.\n",
            "Aytolun and Goncagül set out to kill Selcan. Hayme Ana confronts Aytolun saying that she knows all that she has done. Aytolun threatens to kill Halime by kicking on Halime's womb but gets killed by an arrow shot by Abdurrahman. Goncagül escapes to Köpek. Ertuğrul brings Gümüştekin to the camp and decides to execute him. Köpek again comes to the camp and demands that the criminal be handed over to him. Ertuğrul beheads Gümüştekin's and then hands it over to him much to his anger.\n",
            "Winter ends and spring arrives. Ertuğrul explains the idea of migrating to the Byzantine border. But after opposition, Ertuğrul tells that he will go to the Byzantine border alone if necessary. Gündoğdu goes to Erzurum with his mother for the treatment of his arm from the physicians. Saadettin asks Noyan to capture Yiğit and Dündar, who were training in Konya.\n",
            "Noyan successfully captures Dündar and Yiğit with the information Saadettin gave him. Ertuğrul and his alps could not stop him. Gündoğdu returns from Erzurum. Noyan intrigues Yiğit to become the Sultan and take his father's revenge.\n",
            "Saadettin writes a letter to the Sultan that Şehzade Yiğit has declared himself the Sultan. Turgut is captured by Noyan and is saved by the intervention of Yiğit. Saadettin gives Dündar to Sungurtekin to win his trust. Ertuğrul, Tuğtekin and Sungurtekin raid the cave of Noyan. In the meantime, Noyan captures Gökçe.\n",
            "Gökçe kills Goncagül. Meanwhile, Saadettin plans to trap Tuğtekin. Noyan martyrs both Gökçe and Tuğtekin.\n",
            "Ertuğrul saves Yiğit. Tuğtekin and Gökçe's corpses are brought to the camp. Ertuğrul captures Noyan to take him to the Sultan and also pierces his hand with a nail.\n",
            "Akça Bey becomes the new head of the Dodurga with the help of Saadettin. Sungurtekin goes after Yiğit with the help of Gündoğdu. Meanwhile, Ertuğrul supposedly kills Noyan. Sungurtekin gets injured in the fight.\n",
            "Sungurtekin's injury further increases the tension in Kayı tribe. Gündoğdu becomes the new Bey of Kayı by giving gold coins to the Beys. Ertuğrul declares that he will migrate to the Byzantine border with some people. Köpek attacks on the migrating tribe and Yiğit gets killed.\n",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""};

    String episodeLink[] = {
            "d09bf41544a3365a46c9077ebb5e35c3",
            "da4fb5c6e93e74d3df8527599fa62642",
            "9b8619251a19057cff70779273e95aa6",
            "94c7bb58efc3b337800875b5d382a072",
            "8d5e957f297893487bd98fa830fa6413",
            "1d7f7abc18fcb43975065399b0d1e48e",
            "fa7cdfad1a5aaf8370ebeda47a1ff1c3",
            "a4a042cf4fd6bfb47701cbc8a1653ada",
            "045117b0e0a11a242b9765e79cbf113f",
            "ed265bc903a5a097f61d3ec064d96d2e",
            "68d30a9594728bc39aa24be94b319d21",
            "f4b9ec30ad9f68f89b29639786cb62ef",
            "a9a6653e48976138166de32772b1bf40",
            "5f93f983524def3dca464469d2cf9f3e",
            "eb160de1de89d9058fcb0b968dbbbd68",
            "c75b6f114c23a4d7ea11331e7c00e73c",
            "5ef059938ba799aaa845e1c2e8a762bd",
            "07e1cd7dca89a1678042477183b7ac3f",
            "8d34201a5b85900908db6cae92723617",
            "4c56ff4ce4aaf9573aa5dff913df997a",
            "a0a080f42e6f13b3a2df133f073095dd",
            "202cb962ac59075b964b07152d234b70",
            "c8ffe9a587b126f152ed3d89a146b445",
            "3def184ad8f4755ff269862ea77393dd",
            "069059b7ef840f0c74a814ec9237b6ec",
            "ec5decca5ed3d6b8079e2e7e7bacc9f2",
            "76dc611d6ebaafc66cc0879c71b5db5c",
            "d1f491a404d6854880943e5c3cd9ca25",
            "ccb1d45fb76f7c5a0bf619f979c6cf36",
            "1afa34a7f984eeabdbb0a7d494132ee5",
            "65ded5353c5ee48d0b7d48c591b8f430",
            "9fc3d7152ba9336a670e36d0ed79bc43",
            "7f1de29e6da19d22b51c68001e7e0e54",
            "42a0e188f5033bc65bf8d78622277c4e",
            "3988c7f88ebcb58c6ce932b957b6f332",
            "013d407166ec4fa56eb1e1f8cbe183b9",
            "01f78be6f7cad02658508fe4616098a9",
            "e00da03b685a0dd18fb6a08af0923de0",
            "7f24d240521d99071c93af3917215ef7",
            "1385974ed5904a438616ff7bdb3f7439",
            "f387624df552cea2f369918c5e1e12bc",
            "5e388103a391daabe3de1d76a6739ccd",
            "0f28b5d49b3020afeecd95b4009adf4c",
            "a8baa56554f96369ab93e4f3bb068c22",
            "903ce9225fca3e988c2af215d4e544d3",
            "0a09c8844ba8f0936c20bd791130d6b6",
            "15de21c670ae7c3f6f3f1f37029303c9",
            "2b24d495052a8ce66358eb576b8912c8",
            "a5e00132373a7031000fd987a3c9f87b",
            "47d1e990583c9c67424d369f3414728e",
            "f2217062e9a397a1dca429e7d70bc6ca",
            "7ef605fc8dba5425d6965fbd4c8fbe1f",
            "a8f15eda80c50adb0e71943adc8015cf",
            "11b921ef080f7736089c757404650e40",
            "6e2713a6efee97bacb63e52c54f0ada0",
            "37a749d808e46495a8da1e5352d03cae",
            "b3e3e393c77e35a4a3f3cbd1e429b5dc",
            "1bb91f73e9d31ea2830a5e73ce3ed328",
            "3a0772443a0739141292a5429b952fe6",
            "2a79ea27c279e471f4d180b08d62b00a",
            "1c9ac0159c94d8d0cbedc973445af2da",
            "7bcdf75ad237b8e02e301f4091fb6bc8",
            "6c4b761a28b734fe93831e3fb400ce87",
            "06409663226af2f3114485aa4e0a23b4",
            "140f6969d5213fd0ece03148e62e461e",
            "b73ce398c39f506af761d2277d853a92",
            "bd4c9ab730f5513206b999ec0d90d1fb",
            "82aa4b0af34c2313a562076992e50aa3",
            "0777d5c17d4066b82ab86dff8a46af6f",
            "9766527f2b5d3e95d4a733fcfb77bd7e",
            "7e7757b1e12abcb736ab9a754ffb617a",
            "5737034557ef5b8c02c0e46513b98f90",
            "5878a7ab84fb43402106c575658472fa",
            "006f52e9102a8d3be2fe5614f42ba989",
            "3636638817772e42b59d74cff571fbb3",
            "9b72e31dac81715466cd580a448cf823",
            "16c222aa19898e5058938167c8ab6c57",
            "7dcd340d84f762eba80aa538b0c527f7",
            "149e9677a5989fd342ae44213df68868",
            "1ff8a7b5dc7a7d1f0ed65aaa29c04b1e",
            "f7e6c85504ce6e82442c770f7c8606f0",
            "81448138f5f163ccdba4acc69819f280",
            "bf8229696f7a3bb4700cfddef19fa23f",
            "82161242827b703e6acf9c726942a1e4",
            "38af86134b65d0f10fe33d30dd76442e",
            "96da2f590cd7246bbde0051047b0d6f7",
            "8f85517967795eeef66c225f7883bdcb",
            "8f53295a73878494e9bc8dd6c3c7104f",
            "97e8527feaf77a97fc38f34216141515",
            "fc221309746013ac554571fbd180e1c8",
            "4c5bde74a8f110656874902f07378009",
            "cedebb6e872f539bef8c3f919874e9d7",
            "6cdd60ea0045eb7a6ec44c54d29ed402",
            "eecca5b6365d9607ee5a9d336962c534",
            "9872ed9fc22fc182d371c3e9ed316094",
            "31fefc0e570cb3860f2a6d4b38c6490d",
            "647bba344396e7c8170902bcf2e15551",
            "9dcb88e0137649590b755372b040afad",
            "a2557a7b2e94197ff767970b67041697",
            "fbd7939d674997cdb4692d34de8633c4",
            "28dd2c7955ce926456240b2ff0100bde",
            "35f4a8d465e6e1edc05f3d8ab658c551",
            "d1fe173d08e959397adf34b1d77e88d7",
            "f033ab37c30201f73f142449d037028d",
            "69421f032498c97020180038fddb8e24",
            "43ec517d68b6edd3015b3edc9a11367b",
            "9778d5d219c5080b9a6a17bef029331c",
            "85422afb467e9456013a2a51d4dff702",
            "fe9fc289c3ff0af142b6d3bead98a923",
            "3ef815416f775098fe977004015c6193",
            "93db85ed909c13838ff95ccfa94cebd9",
            "c7e1249ffc03eb9ded908c236bd1996d",
            "2a38a4a9316c49e5a833517c45d31070",
            "7647966b7343c29048673252e490f736",
            "8613985ec49eb8f757ae6439e879bb2a",
            "13f320e7b5ead1024ac95c3b208610db",
            "54229abfcfa5649e7003b83dd4755294",
            "92cc227532d17e56e07902b254dfad10",
            "98dce83da57b0395e163467c9dae521b",
            "f4be00279ee2e0a53eafdaa94a151e2c",
            "812b4ba287f5ee0bc9d43bbf5bbe87fb",
            "26657d5ff9020d2abefe558796b99584",
            "e2ef524fbf3d9fe611d5a8e90fefdc9c",
            "37f0e884fbad9667e38940169d0a3c95",
            "ed3d2c21991e3bef5e069713af9fa6ca",
            "d64a340bcb633f536d56e51874281454",
            "ac627ab1ccbdb62ec96e702f07f6425b",
            "f899139df5e1059396431415e770c6dd",
            "38b3eff8baf56627478ec76a704e9b52",
            "ec8956637a99787bd197eacd77acce5e",
            "6974ce5ac660610b44d9b9fed0ff9548",
            "0fcbc61acd0479dc77e3cccc0f5ffca7",
            "298f95e1bf9136124592c8d4825a06fc",
            "c9e1074f5b3f9fc8ea15d152add07294",
            "65b9eea6e1cc6bb9f0cd2a47751a186f",
            "f0935e4cd5920aa6c7c996a5ee53a70f",
            "a97da629b098b75c294dffdc3e463904",
            "a3c65c2974270fd093ee8a9bf8ae7d0b",
            "2723d092b63885e0d7c260cc007e8b9d",
            "df877f3865752637daa540ea9cbc474f",
            "698d51a19d8a121ce581499d7b701668",
            "7f6ffaa6bb0b408017b62254211691b5",
            "73278a4a86960eeb576a8fd4c9ec6997",
            "c399862d3b9d6b76c8436e924a68c45b",
            "5fd0b37cd7dbbb00f97ba6ce92bf5add",
            "33e8075e9970de0cfea955afd4644bb2",
            "2b44928ae11fb9384c4cf38708677c48",
            "65658fde58ab3c2b6e5132a39fae7cb9",
            "c45147dee729311ef5b5c3003946c48f",
            "5ea1649a31336092c05438df996a3e59",
    };
    ListView listView;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView = (ListView) findViewById(R.id.customListView2);
        CustomBaseAdapter customerBaseAdapter = new CustomBaseAdapter(getApplicationContext(), fruitList, fruitImages, episodeDescription);
        listView.setAdapter(customerBaseAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int epNum = i+1;

                //for calling mxplayer
                //CallPlayer(episodeLink[i],"Episode "+epNum, epNum);

                //save ertugrul episode info for player to get episode number, series name, link
                SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Episode", String.valueOf(epNum));
                editor.putString("Image", "1");
                editor.putString("Link", episodeLink[i]);
                editor.commit();

                menuObj.active = true;

                //exit this activity and go to player
                finish();
                Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                startActivity(intent);
            }
        });
        //updateEpisodeProgress();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
        finish();
    }

    //for calling mxplayer
    void CallPlayer(String url, String epName, int episodeNumber) {

        //saves episode data
        SharedPreferences info = getSharedPreferences("info", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = info.edit();
        myEdit.putString("Episode", Integer.toString(episodeNumber));
        myEdit.putString("Image", "1");
        myEdit.putString("Link",url);
        myEdit.commit();

        title = epName;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.mxtech.videoplayer.ad");
        intent.setClassName("com.mxtech.videoplayer.ad", "com.mxtech.videoplayer.ad.ActivityScreen");
        Uri videoUri = Uri.parse("https://maher.xtremestream.co/player/load_m3u8_xtremestream.php?data=" + url/* + "&q=" + quality*/);
        intent.setDataAndType(videoUri, "video/*");
        intent.setPackage("com.mxtech.videoplayer.ad"); // com.mxtech.videoplayer.pro
        //intent.putExtra("return_result", true);
        intent.putExtra("title", epName);
        startActivityForResult(intent, 0);
    }


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)

            // -1 RESULT_OK : Playback was completed or stopped by user request.
            //Activity.RESULT_CANCELED: User canceled before starting any playback.
            //RESULT_ERROR (=Activity.RESULT_FIRST_USER): Last playback was ended with an error.

            if (data.getAction().equals("com.mxtech.intent.result.VIEW")) {
                pos = data.getIntExtra("position", -1); // Last playback position in milliseconds. This extra will not exist if playback is completed.
                dur = data.getIntExtra("duration", -1); // Duration of last played video in milliseconds. This extra will not exist if playback is completed.
                String cause = data.getStringExtra("end_by"); //  Indicates reason of activity closure.

                SharedPreferences info = getSharedPreferences("info", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = info.edit();

                myEdit.putString(title + "p", String.valueOf(pos));
                myEdit.putString(title + "d", String.valueOf(dur));

                int resID = getResources().getIdentifier(title + "p", "id", getPackageName());

                ProgressBar pBar;
                pBar = (ProgressBar) findViewById(resID);

                pBar.setMax(dur);
                pBar.setProgress(pos);



            }
    }

    protected void updateEpisodeProgress() {
        SharedPreferences info = getSharedPreferences("info", MODE_PRIVATE);

        //Keep number '1' more than max. for e.g. if '5' episodes then number will be '6' in variable.
        int Season1 = 3, Season2 = 8, Season3 = 2;


        for (int i = 1; i < Season1; i++) {

            String position = info.getString("S1E"+i+"p", "-1");
            String duration = info.getString("S1E"+i+"d", "-1");

            int resID = getResources().getIdentifier("S1E"+i+"p", "id", getPackageName());

            ProgressBar pBar;
            pBar = (ProgressBar) findViewById(resID);

            pBar.setMax(Integer.parseInt(duration));
            pBar.setProgress(Integer.parseInt(position));
        }

        for (int i = 1; i < Season2; i++) {

            String position = info.getString("S2E"+i+"p", "-1");
            String duration = info.getString("S2E"+i+"d", "-1");

            int resID = getResources().getIdentifier("S2E"+i+"p", "id", getPackageName());

            ProgressBar pBar;
            pBar = (ProgressBar) findViewById(resID);

            pBar.setMax(Integer.parseInt(duration));
            pBar.setProgress(Integer.parseInt(position));
        }

        for (int i = 1; i < Season3; i++) {

            String position = info.getString("S3E"+i+"p", "-1");
            String duration = info.getString("S3E"+i+"d", "-1");

            int resID = getResources().getIdentifier("S3E"+i+"p", "id", getPackageName());

            ProgressBar pBar;
            pBar = (ProgressBar) findViewById(resID);

            pBar.setMax(Integer.parseInt(duration));
            pBar.setProgress(Integer.parseInt(position));
        }*/
}

