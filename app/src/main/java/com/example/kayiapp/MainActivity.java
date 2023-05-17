package com.example.kayiapp;

import static android.provider.CalendarContract.CalendarCache.URI;

import static com.example.kayiapp.Menu.EPISODE;
import static com.example.kayiapp.Menu.IMAGE;
import static com.example.kayiapp.Menu.LINK;
import static com.example.kayiapp.Menu.SHARED_PREFS;
import static com.example.kayiapp.Menu.WEB_URL;
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

public class MainActivity extends AppCompatActivity {

    String title;

    String fruitList[] = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60","61","62","63","64","65","66","67","68","69","70","71","72","73","74","75","76","77","78","79","80","81","82","83","84","85","86","87","88","89","90","91","92","93","94","95","96","97","98","99","100","101","102","103","104","105","106","107","108","109","110","111","112","113","114","115","116","117","118","119","120","121","122","123","124"};
    int fruitImages [] = {R.drawable.osman_img3};

    Menu menuObj = new Menu();

    public String episodeDescription[] = {
            "Dündar Bey, substitute Bey of the Kayı tribe, seeks peace with the Tekfur of Kulucahisar Castle, Yorgopolos, who is killed on the same day by his evil wife Sofia. The blame is pinned on Dündar's nephew, Osman Bey. Meanwhile, Bala, the daughter of Turkish Sheikh, Edebali, also attempts to recover some \"entrustments\" for her father.",
            "Bala finds the entrustments, however, Osman captures Bala while attempting to find Yorgopolos' killer and suspects her of killing him. Bala takes Osman to her father and Osman allies with Edebali and his organization, the Ahi brotherhood, against Sofia and her father, Yannis' evil organization, the \"Margarit Monks\". Sofia orders Theokles, her veteran warrior, to kill Osman.\n",
            "In Theokles' ambush, Osman captures Theokles, only to be set free by Dündar's son, Batur. Theokles is later killed. Osman falls in love with Bala while Sofia, in search of the entrustments, enlists Prince Salvador to invade the Ahi bazaar. Bala is kidnapped due to this by him.\n",
            "Sofia orders Prince Salvador to take Bala to Yannis where she is constantly tortured by him in order for her to reveal the location of the entrustments but Osman later rescues her.\n",
            "It is proven that Osman didn't kill the Tekfur. To assure Dündar, Sofia executes one of Yannis' men in front of him claiming that he was Yannis with the help of her father.\n",
            "Selcan Hatun, Osman's aunt, arrives, she treats Osman as her own son and Osman treats her as his own mother. Osman asks her to go and ask Edebali for Bala's hand angering Dündar for Osman hadn't asked him. Osman falls into Prince Salvador's ambush and he and his alps are captured.\n",
            "Batur, Dündar's son, rescues Osman for a reputation in the tribe after one of Salvador's men is tricked. Edebali declines Osman's proposal leaving him devastated and upset.\n",
            "Sofia tasks her lover, Kalanoz, to kill Şeyh Edebali but is killed in this attempt by Osman. Bamsı, Osman's loyal companion, converts Salvador to Islam. Salvador's new name is Sıddık.\n",
            "Osman steals all the tax gold that was being transported illegally to Alişar Bey, the Selçuk Sançak Bey, by the Mongols. Yannis, disguised as a clairvoyant, proves to Alişar that Osman stole the gold. Alişar becomes an enemy of Osman.\n",
            "Balgay, a Mongol commander, captures Söğüt and kills many women and children. He offers Osman that he will give him the Sançak Beylik and some more land if he was to become loyal to him. Osman rejects Balgay's offer.\n",
            "Osman, with many difficulties, manages to escape the town with his alps. Balgay meets Yannis and Sofia and they decide to ally against Osman. Balgay even raids the Kayı tribe and has Osman's entire family in hostage until he comes. Konur, one of Osman's alps, also uncovers the fact that one of Balgay's men, Kongar, is his long-lost brother.\n",
            "Osman, who has met the Aksakals, arrives in the tribe and gives Balgay Genghis Han's dagger in order to leave him and his tribe alone. Osman tasks Salvador to spy for him in Kulucahisar Castle, however, the cunning Sofia still doesn't trust him too much. Meanwhile, the tribe council, mainly Dündar, decides to expel Osman for rebelling against the Mongols. Osman later recaptures Söğüt with his alps.\n",
            "Dündar is given the Sançak Beylik, angering Alişar, and Osman takes back the dagger he gave to Balgay secretly through an operation at his camp, humiliating him in front of the Mongol leaders. Bala is wounded by some Mongols and now cannot produce a baby.\n",
            "Osman falls into Balgay, Dündar, and Batur's trap but is saved when Geyhatu, the Mongol Viceroy, sends Commander Köni to take Osman to him. Balgay later tasks his right-hand men, Cerkutay and Kongar to kill Köni and they succeed, however, Osman also escapes with the help of Bamsı.\n",
            "After he kidnaps Sofia, Osman tells Yannis that he can have his daughter back if he asked Balgay to come to his castle, in the way he would ambush him. The ambush is successful but Batur is killed by Alişar, who has the intention of making Dündar and Osman fight.\n",
            "Alişar pins the blame on Osman resulting in Dündar attacking Osman during the ambush. In Dündar's attack, Alişar, who is hidden behind the trees, aims at killing Osman with his arrow but accidentally hits Dündar instead. Balgay escapes but Gündüz, Osman's elder brother, successfully becomes the new substitute Bey of the Kayı tribe.\n",
            "After his meeting with the Aksakals, Osman saves Princess Adelfa, a Byzantine princess being forced to marry Geyhatu. After saving her, he takes her to his tribe and asks Bala and Selcan to take special care of her.\n",
            "Zöhre, Dündar's devious wife who even had Gündüz kidnapped by Balgay earlier, plans to take Adelfa to Alişar in spite of Bala and Selcan through her adoptive daughter, Burçin.\n",
            "Kongar reverts to Islam and becomes Göktug after a contract is shown to him that he was actually bought by Balgay from a slave-trader. Meanwhile, Osman is tasked by the Aksakals to save his uncle, Sungurtekin, who was spying for them in the Margarit Monks and his betrayal is now caught, Osman succeeds and embraces his thought-dead uncle.\n",
            "Osman ambushes Dündar and Zöhre, who were taking their daughter, Aygül, to get her married to Alişar, he sought to show them that he didn't kill Batur. He trusts Aygül that she believed him that he didn't kill Batur, however, as she didn't trust him from the start, when she is let go, she tells Alişar everything and asks him to save her parents. Later, Aygül's parents are freed because of her betrayal.\n",
            "Balgay captures Osman while Böke, the Mongol commander who informed the monks about Sungurtekin's betrayal, supposedly allies with Balgay to save his life, however, with the help of Göktug, disguised as Kongar, Osman escapes but is severely wounded by arrows.\n",
            "Göktug continues to spy for Osman at Balgay's camp while Osman recovers with the help of Şeyh Edebali and sees a dream which results in Edebali allowing Osman to marry Bala.\n",
            "Osman tasks Gündüz and Bala to invade Alişar's mansion where they are successful. Meanwhile, Osman captures Alişar when he is away and forces him to reveal to Dündar that he killed Batur. After seeing this, Dündar begs for forgiveness. Böke also dies in an ambush and Osman takes Alişar to the tribe and is ready to behead him only to be stopped by Geyhatu's commander, Subutay.\n",
            "Osman ignores Subutay's threats and beheads Alişar. Sofia escapes the Emperor who sentenced Yannis and most of the Margarit Monks to death. Hazal[v], Dündar's first wife, arrives and kills Zöhre for poisoning Osman and Bala's wedding food, which was given to her indirectly through Sofia. Meanwhile, Balgay finds out that Göktug has been betraying him and gives him a potion that messes up his memory and changes him into Kongar again.\n",
            "Aygül is currently mentally ill after she learnt the truth about Alişar. Sıddık's betrayal is caught by Sofia and thus he is martyred while Subutay allies with Balgay and Sofia to destroy the Kayı tribe in an attack. Osman, who is aware of the attack, prepares for it and therefore, Subutay's plans backfire.\n",
            "Subutay is beheaded, however, Sofia escapes with the help of Balgay. Kongar becomes a Muslim again after remembering everything but bids farewell to Konur, who was accidentally killed by him when he was drunk with the potion. In an epic showdown, Balgay is supposedly killed by Osman along with the supposed death of Cerkutay by Göktug in the presence of Sofia and her 'dark knights', a special sort of knights, who keep on fighting till they die. However, Cerkutay manages to survive the attack.\n",
            "The 'dark knights' injure Osman very badly and everyone believes he is dead, however, Osman is actually alive but he only tells Gündüz, Bala and Selcan. Taking advantage, Osman conquers Kulucahisar Castle and kills Sofia in the process. Aygül, who is mentally ill, attempts to kill Hazal because she killed her mother, however, the arrow accidentally kills Burçin instead.[40]\n",
            "Tekfur Alexis of İnegöl seeks revenge on the Kayı tribe for conquering Kulucahisar. He invades a caravan of the Kayı tribe, Osman, takes his revenge by defeating him in a battle. After the battle, the Emperor, concerned over the Turk threat on his empire, sends Aya Nikola to İnegöl Castle. Nikola kills Alexis for his failure and snatches the castle from him. Nikola also frees an old friend from prison, Flatyos. Ertuğrul also later returns towards the end after a year in Konya.\n",
            "Nikola arrives in the nomad camp and claims he wants peace while Osman thinks he is about to meet his father but falls into an ambush set by Yavlak Arslan, Hazal Hatun's brother. Meanwhile, Osman's elder brother, Savcı, also returns from Crimea. Ertuğrul goes to meet Arslan Bey of the Çobanoğlu tribe and warns him not to become a traitor as he's dealt with them his whole life. Osman, who escapes Arslan's ambush, ambushes him and Geyhatu's son, Möngke's camp. However, Arslan and Möngke escape.\n",
            "Arslan tasks Cerkutay, who is alive, to go to Geyhatu, who already hates him because of him being loyal to Balgay, and tell him that Osman captured Möngke. Geyhatu, who is in Konya planning to kill his brother, Argun, is angered over this and allows Cerkutay to live if he is loyal to him. Meanwhile, Söğüt perishes due to the disease spread by Flatyos on the orders of Nikola followed by an explosion done by the same people.\n",
            "The relations between Savcı and Osman become tenser, Osman is angered when Ertuğrul sends Savcı to explain to Geyhatu that Osman didn't kidnap Möngke. When Savcı arrives at Geyhatu's camp, he is given only a few days to find Möngke otherwise his tribe will be destroyed. After Savcı tricks one of Cerkutay's men into telling him Möngke's location, he falls into Möngke and Arslan's trap and is captured. Möngke seeks to kill his father to become the Viceroy and then the Great Han while pinning the blame on Osman.\n",
            "The time period given by Geyhatu ends and Geyhatu arrives in Osman's tribe. Osman saves Savcı and he returns to the tribe with the captured Möngke. Ertuğrul continues to stay on his death bed.\n",
            "Möngke's treachery is proven after he is forced to reveal the truth, however, he doesn't manage to say the truth about Arslan thus Arslan survives and Möngke is killed by Geyhatu. In the meantime, Aya Nikola and Flatyos successfully capture Kulucahisar Castle.\n",
            "Osman is alerted that a distant Cuman Turk tribe has been invaded by the Emperor and the Bey's daughter, Targun, is being taken captive. Osman rescues Targun and takes her to the tribe. However, Targun is betraying Osman, she spies for Aya Nikola to save her father, İnal Bey, who is in Aya Nikola's captive. Hazal also teases Bala that Targun was brought by Osman because he wanted to marry her for a child which she couldn't produce.\n",
            "After Nikola is stabbed by Osman, Flatyos attempts to take revenge on Osman with a gunpowder plot on Söğüt. Osman however, is aware of the attack and Flatyos' plans backfire. Flatyos is captured and is taken to Kulucahisar where the \"incompetent\" Dündar and Savcı are saved by Osman who were about to be beheaded by Nikola.\n",
            "Targun goes to meet her father at Kulucahisar but is spotted, when she returns, Osman captures her and she is forced to tell the truth. Osman vows to help her save her father and also decides to marry her after Bala says that he needs an heir to become the Bey. He also attacks Kulucahisar while Nikola goes to meet Arslan at the Çobanoğlu tribe.\n",
            "Osman reconquers Kulucahisar, although İnal Bey is taken by Nikola's knights to İnegöl. Bala and Targun's relations tensen a little and Dündar's evil plans are also revealed, he seeks to become the Bey of the tribe.\n",
            "The tensions escalate further upon the death of Argun, who was killed by Geyhatu, and Geyhatu's visit to Tabriz in order to become the Han of the İlhanlı (transl. Ilkhanate). Nikola vows to ally with the powerful Geyhatu in order to destroy the Turks. Ertuğrul also recovers and stops Savcı from becoming the Bey (which is a part of Dündar's plans) while Yavlak allies with Osman to help him against Geyhatu.\n",
            "Targun and Osman make a plan to kill Nikola and rescue İnal Bey in which they succeed in rescuing İnal but fail to kill Nikola and only injure him. Ertuğrul dies just before he could announce that Osman is the new Bey.\n",
            "The tribe elections are held to decide the new Bey. Dündar comes face-to-face with Osman however, Osman is elected as the new Bey much to Dündar's despair and anger. Meanwhile, a new threat against the Kayı tribe emerges after Petrus, sent by the Pope, arrives to help Nikola kill Osman but also has a secret plan which he doesn't tell Nikola about. Bayhoca, Savcı's son, also returns.\n",
            "After a series of events including Bala being poisoned by Targun, Targun and Göktug are exiled, which is part of Osman's plans. Dündar has a meeting with Nikola which Osman is aware of resulting his beating when he returns. Petrus' real plans are also revealed, he wants to know what the Turks of Anatolia are planning by seizing some scrolls.\n",
            "Dündar and the Kayı make a trade deal with Tüccar Melik and Süleyman (Simon and Petrus), Ertuğrul comes in Osman's dream reminding him of his will for his second marriage and Nikola is made the \"Tekfur of Tekfurs\" while his army from Constantinople, led by Alexander, is destroyed by Osman in a great battle. Alexander survives.\n",
            "Bala seeks to marry Aksu Hatun to Osman as per Ertuğrul's will, although Aksu is Hazal's adoptive daughter and unknowingly ends up working in her devious schemes. The Kayı flag is also changed by Osman along with Bayhoca being captured by Nikola after a series of events.\n",
            "Going against his loved ones' advice, after Savcı is tricked and wounded by Flatyos, Osman agrees to surrender Kulucahisar in exchange for Bayhoca. However, he makes a plan and re-conquers the castle immediately after surrendering it, killing Alexander. Meanwhile, a desperate Lena seeks her former lover Flatyos' help, angering Savcı. İdris, Petrus' spy in the Kayı, also begins to set a trap for Bamsı on Petrus' orders, whilst Göktug and Targun return and offer Flatyos an alliance.\n",
            "As the allied enemies, Nikola, Targun and Petrus continue to stir traps for Osman, Targun ambushes Bala while İdris leads Bamsı into a trap set by Aya Nikola in order to seize the scrolls with Osman's plans for Anatolia. When Osman is alerted, he sets out with half his alps to find Bamsı, only to fall in Nikola's trap and have his tribe pillaged by Targun for the scrolls and a failed attempt by Dündar to become the Bey. Aksu is also killed by Targun before she can confess to Bala about Dündar and Hazal's misde\n",
            "Göktuğ helps Bala defeat Targun in a trap, with Bala eventually killing her. Meanwhile, Osman is saved from Nikola's ambush by Malhun Hatun, a woman requesting Osman for some land for her tribe of 1000. As Malhun settles in the tribe, she questions Osman about his plans. Göktug is made leader of the Cuman Turks, whilst Petrus and Simon demand compensation from Osman for allegedly being attacked. İdris is caught as a spy by Osman, although he is killed by Dündar before he can reveal his treach\n",
            "Osman goes after İdris' killer, but Dündar is saved by Petrus. Meanwhile, Göktuğ learns that Nikola is travelling to Constantinople, and sends word to Osman. Dündar later allies with Petrus and reveals Osman's intentions to ambush Nikola whilst he is on his journey. Meanwhile, the cynical Malhun discovers Dündar's thumb ring, having been dropped when he was in Söğüt, and attempts to find the traitors in the Kayı. As Osman's ambush is foiled by Nikola after learning of it from Petrus, Bayhoca is shot and wounded by Flat\n",
            "Osman and his alps are forced to retreat from the battle, whilst Bayhoca later dies from his wounds. Flatyos is soon captured by Osman, and is taken to the Kayı tribe in order to make him reveal the traitor in the tribe. However, on Dündar's urging, Savcı and Lena kill Flatyos to avenge their son, inadvertently covering Dündar's tracks. Meanwhile, Petrus allies with Geyhatu's commander Togay, who arrives seeking to annihilate the Bayındır and Kayı tri\n",
            "Togay, revealed to be Baycu Noyan's son, attacks a meeting of Turkmen Beys chaired by Osman in Söğüt, wounding Ömer Bey. Seeking to sow discord amongst the Turks, Togay frames the Dodurga Bey İvaz Bey for the attack, although his innocence is later revealed. Meanwhile, Lena, having given birth to a son named Ertuğrul, accepts Islam with the help of Şeyh Edebali. Suspecting Dündar's treachery, Osman attempts to set a trap for Dündar and Togay, although Dündar outwits Osman, causing Togay to attack and wound Mal\n",
            "Osman captures Suci, one of Togay's henchmen. However, Suci is also Cerkutay's blood brother and friend, compelling Cerkutay to save his friend, leading to the tribe believing that Cerkutay is a traitor. Cerkutay, actually serving Osman, later helps the Kayı attack Togay's forces. Meanwhile, a recovered Malhun is sent by her father to İnegöl to ally with Nikola against Togay, although she is captured in the castle by Togay, who has allied with Nikola. Osman also captures Nikola's ally Tekfur Aris, seeking to ally with him, although Togay delivers an ultimatum to surrender the Tekfur in exchange for Mal\n",
            "Osman decides to use Tekfur Aris as bait to trap Nikola. However, Togay kills Aris on the way, providing Nikola with an excuse to keep Malhun in captivity. Nikola also kills Zülfikar Derviş, Osman's spy in İnegöl. Nikola also releases Malhun to gain the support of Ömer Bey, who seeks to sell him horses in order to infiltrate and conquer the castle. The sale of horses angers Osman, who orders them to be seized. Meanwhile, Togay ambushes some of the Kayı on the way back from Boran Alp's unfinished wedding, wounding Gonca Hatun and killing Abdurrahman G\n",
            "After Togay leaves for Konya, his fellow commander Camuha arrives. After Nikola learns Camuha's whereabouts from Dündar, he informs Osman, leading to a successful ambush, although Camuha escapes. Meanwhile, Osman attacks the incoming Kalanoz, younger brother of Kalanoz, after learning of his arrival indirectly from the Mongols, killing many Byzantine soldiers. He invites all Turkmen tribes to join the attack except the Bayındırlı, angering Ömer Bey. Şeyh Edebali later calls Osman and Ömer Bey to Söğüt to resolve their differences, leading to Ömer Bey handing over Dündar's ring to Osman, exposing Dündar as a trai\n",
            "Dündar flees from Söğüt after being exposed as a traitor, and takes refuge with Nikola in İnegöl. The revelation that Dündar is a traitor infuriates Gündüz and Savcı and saddens Aygül. Meanwhile, Petrus and his right-hand man Simon attempt to kill Hazal to cover their tracks, but are stopped by Osman Bey and are captured, with Hazal put under house arrest. Osman sends Gündüz as a messenger to İnegöl and offers a ransom for Dündar, although Nikola declines and demands more gold via Ömer Bey. Eventually, Osman uses Simon to lure Dündar into a trap, capturing \n",
            "Dündar is taken to the tribe and sentenced to death by Osman. Just as he is about to be strangled to death by the alps, he requests to be shot by Osman instead, using the arrow that killed Bayhoca per Savcı's wish. The remorseful Dündar also faces Aygül's anger, and reveals a plot by Nikola to use underground tunnels to invade Söğüt. Dündar is eventually executed, whilst Hazal is exiled to the Çobanoğlu tribe. Meanwhile, Osman sends Malhun Hatun to steal gunpowder from İnegöl, planning to use it to destroy the underground tunnels and kill the invading soldiers. While Togay harasses Tekfur Yason,[y] brother of Tekfur Aris, over taxes, Osman heads to battle against Nik\n",
            "Due to the pressure of the Mongols, Tekfur Yason imposes heavy taxes, causing the people of Yarhisar to take shelter with Osman Bey. Nikola also finds out about Simon and Petrus being captured by Osman and kills Simon after realising Osman is using him. Refusing Togay's demand to turn over the refugees, Osman Bey causes the Byzantine-Mongol alliance to be re-established. While Togay plans a major operation against Kayı Tribe, Malhun Hatun, who wants to take Osman's side in this war, confronts her father, Umur \n",
            "Osman Bey confronts the Byzantine-Mongol army with the support of Malhun Hatun. In the ensuing battle, Savcı Bey is stabbed by Kalanoz in the back and dies, which prompts Osman Bey to kill Kalanoz in revenge. Nikola, who gathers troops from the local landlords, plans to raid Umur Bey’s tribe in response to Malhun Hatun's participation, whilst Umur Bey, decides to exile his daughter for fighting against his will. Looking for ways to break the Byzantine-Mongol alliance, Osman Bey agrees to pay taxes to the Mongols, surprising everyone. Togay, who is strengthened by the lands given to his command, plans to turn this situation into an opportunity to consolidate his domination in the reg\n",
            "Osman Bey's expulsion of the ambassador who comes to the tribe to receive the tax causes Togay to break his truce. This causes the Seljuk Sultan, Mesud II, to summon Osman Bey to Konya to punish him in order to protect him from the wrath of the Mongols. Learning that Osman Bey refused to pay taxes to the Mongols from a spy in the tribe, Nikola takes action to increase his influence by taking advantage of the Mongol-Turkish war. Bamsı Bey also kills Petrus after Osman realises Simon was killed by Nikola. Cerkutay, on the other hand, becomes a Muslim under the witness of Sheikh Edebali. Umur Bey develops a plan to attack Togay, which is opposed by Osman. After a confrontation between the pair, Sheikh Edebalı tells Osman Bey that he must marry Malhun Hatun in order to gain the support of Umur Bey and achieve the goal of uniting the Turkish tri\n",
            "After Sheikh Edebalı intervenes and prevented a possible conflict between Osman Bey and Umur Bey, Umur Bey stands down. Meanwhile, Nikola takes action to destroy the truce between Osman Bey and Togay, with Osman's plan to trap Togay being ruined. While Osman Bey, sends Göktuğ as an envoy to Togay to ensure he remains loyal to the agreement; Sheikh Edebali takes on the task of persuading Umur Bey about marriage. Osman Bey, on the other hand, explains to Bala Hatun that he decided to marry Malhun Hatun after consulting with Sheikh Edeb\n",
            "Umur Bey and his alps are captured by Togay after ignoring Osman Bey's order not to launch an attack. Meanwhile, Togay's commander Camuha raids the vulnerable Bayındır tribe with his sentries, with Malhun Hatun and a handful of alps battling to defend the tribe. Later. Umur Bey, left alone and trapped by Nikola, regrets his failure. Osman Bey, who takes immediate action to heal the wounds of Umur Bey, holds Umur Bey to account for disobeying his comm\n",
            "A wedding ceremony is set up in Kayı Tribe for the marriage of Osman Bey and Malhun Hatun. While the wedding is being held, Bala Hatun grieves despite her consent to this marriage. With the arrival of the winter season, after the wedding ceremony, it is decided that Kayı tribe will migrate to Domaniç Plateau. While preparations continue in the tribe, Togay breaks the agreement and decides to attack Osman Bey, despite the warnings of the Mongol governor Yargucu. In the ensuing battle, Togay kills Bamsı Beyrek after a fi\n",
            "After Bamsı Bey's martyrdom, Osman Bey takes an oath to avenge him. Togay, meanwhile, develops an alliance with Nikola and establishes a large army to strike a last blow to the Kayıs. Togay, who enlarges his army with the soldiers he receives from Nikola, stands against Osman Bey and his alps in the battlefield. However, Nikola withdraws from the battle after having a prior agreement with Gündüz Bey, although Governor Yargucu arrives and takes Togay into cust\n",
            "After Osman lures Togay from custody into a trap, he captures and beheads him in Söğüt. The abduction and execution of Togay angers the Governor Yargucu, who subsequently demands Kayı Tribe pay a three-year tax within three days. Yargucu also forms an alliance with Nikola. Later, Malhun Hatun reveals to Osman that she is pregnant and Göktuğ's lover Zoe is revealed to be serving Nik\n",
            "Osman Bey takes action to prepare for a war he will wage against the Byzantine army led by a commander named Dukas. Osman goes to Konya to meet with the Seljuk sultan, whilst Governor Yargucu, plans a move against Osman Bey. Increasing his power with the arrival of Commander Dukas in İnegöl with his army, Nikola thinks that he has the opportunity he is looking for to drive the Turks out of the edge. Bala Hatun, who learns that Osman Bey will have a child, tries to control her feelings and takes a step towards Malhun Hatun. Osman Bey, on the other hand, goes to battle with Dukas' Byzantine a\n",
            "Before the battle, Osman raids the Byzantines and seizes ammunition, before going to battle with the support of Turkmen and Seljuk forces. During the battle, Dukas and local Tekfurs are killed by Osman's forces, with Nikola and Helen escaping alive, and Turhan Alp being martyred. Meanwhile, Zoe tries to poison the pregnant Malhun Hatun but is caught and arrested. After the battle, Osman's son Orhan is born, whilst Bala reveals to Osman that she is pregnant and Osman decides to establish a state after visiting the Aksakals.\n",
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

    public static String[] episodeLink = {
            "be83ab3ecd0db773eb2dc1b0a17836a1",
            "cb70ab375662576bd1ac5aaf16b3fca4",
            "c24cd76e1ce41366a4bbe8a49b02a028",
            "291597a100aadd814d197af4f4bab3a7",
            "7a614fd06c325499f1680b9896beedeb",
            "0f49c89d1e7298bb9930789c8ed59d48",
            "6883966fd8f918a4aa29be29d2c386fb",
            "49182f81e6a13cf5eaa496d51fea6406",
            "d296c101daa88a51f6ca8cfc1ac79b50",
            "e165421110ba03099a1c0393373c5b43",
            "289dff07669d7a23de0ef88d2f7129e7",
            "577ef1154f3240ad5b9b413aa7346a1e",
            "01161aaa0b6d1345dd8fe4e481144d84",
            "539fd53b59e3bb12d203f45a912eeaf2",
            "ac1dd209cbcc5e5d1c6e28598e8cbbe8",
            "555d6702c950ecb729a966504af0a635",
            "335f5352088d7d9bf74191e006d8e24c",
            "f340f1b1f65b6df5b5e3f94d95b11daf",
            "e4a6222cdb5b34375400904f03d8e6a5",
            "9188905e74c28e489b44e954ec0b9bca",
            "0266e33d3f546cb5436a10798e657d97",
            "38db3aed920cf82ab059bfccbd02be6a",
            "4ffce04d92a4d6cb21c1494cdfcd6dc1",
            "3cec07e9ba5f5bb252d13f5f431e4bbb",
            "621bf66ddb7c962aa0d22ac97d69b793",
            "077e29b11be80ab57e1a2ecabb7da330",
            "6c9882bbac1c7093bd25041881277658",
            "19f3cd308f1455b3fa09a282e0d496f4",
            "03c6b06952c750899bb03d998e631860",
            "c52f1bd66cc19d05628bd8bf27af3ad6",
            "fe131d7f5a6b38b23cc967316c13dae2",
            "f718499c1c8cef6730f9fd03c8125cab",
            "d96409bf894217686ba124d7356686c9",
            "502e4a16930e414107ee22b6198c578f",
            "67e103b0761e60683e83c559be18d40c",
            "cfa0860e83a4c3a763a7e62d825349f7",
            "a4f23670e1833f3fdb077ca70bbd5d66",
            "b1a59b315fc9a3002ce38bbe070ec3f5",
            "36660e59856b4de58a219bcf4e27eba3",
            "8c19f571e251e61cb8dd3612f26d5ecf",
            "d6baf65e0b240ce177cf70da146c8dc8",
            "e56954b4f6347e897f954495eab16a88",
            "f7664060cc52bc6f3d620bcedc94a4b6",
            "eda80a3d5b344bc40f3bc04f65b7a357",
            "8f121ce07d74717e0b1f21d122e04521",
            "06138bc5af6023646ede0e1f7c1eac75",
            "39059724f73a9969845dfe4146c5660e",
            "7f100b7b36092fb9b06dfb4fac360931",
            "9b698eb3105bd82528f23d0c92dedfc0",
            "4734ba6f3de83d861c3176a6273cac6d",
            "d947bf06a885db0d477d707121934ff8",
            "63923f49e5241343aa7acb6a06a751e7",
            "db8e1af0cb3aca1ae2d0018624204529",
            "20f07591c6fcb220ffe637cda29bb3f6",
            "07cdfd23373b17c6b337251c22b7ea57",
            "d395771085aab05244a4fb8fd91bf4ee",
            "92c8c96e4c37100777c7190b76d28233",
            "e3796ae838835da0b6f6ea37bcf8bcb7",
            "6a9aeddfc689c1d0e3b9ccc3ab651bc5",
            "46ba9f2a6976570b0353203ec4474217",
            "0e01938fc48a2cfb5f2217fbfb00722d",
            "16a5cdae362b8d27a1d8f8c7b78b4330",
            "918317b57931b6b7a7d29490fe5ec9f9",
            "48aedb8880cab8c45637abc7493ecddd",
            "093f65e080a295f8076b1c5722a46aa2",
            "f90f2aca5c640289d0a29417bcb63a37",
            "9c838d2e45b2ad1094d42f4ef36764f6",
            "1700002963a49da13542e0726b7bb758",
            "53c3bce66e43be4f209556518c2fcb54",
            "c06d06da9666a219db15cf575aff2824",
            "735b90b4568125ed6c3f678819b6e058",
            "eba0dc302bcd9a273f8bbb72be3a687b",
            "14bfa6bb14875e45bba028a21ed38046",
            "218a0aefd1d1a4be65601cc6ddc1520e",
            "7d04bbbe5494ae9d2f5a76aa1c00fa2f",
            "a516a87cfcaef229b342c437fe2b95f7",
            "c3c59e5f8b3e9753913f4d435b53c308",
            "9fd81843ad7f202f26c1a174c7357585",
            "55a7cf9c71f1c9c495413f934dd1a158",
            "2d6cc4b2d139a53512fb8cbb3086ae2e",
            "2b8a61594b1f4c4db0902a8a395ced93",
            "c5ab0bc60ac7929182aadd08703f1ec6",
            "0ff39bbbf981ac0151d340c9aa40e63e",
            "55743cc0393b1cb4b8b37d09ae48d097",
            "26408ffa703a72e8ac0117e74ad46f33",
            "68264bdb65b97eeae6788aa3348e553c",
            "8757150decbd89b0f5442ca3db4d0e0e",
            "109a0ca3bc27f3e96597370d5c8cf03d",
            "7f5d04d189dfb634e6a85bb9d9adf21e",
            "10a5ab2db37feedfdeaab192ead4ac0e",
            "e555ebe0ce426f7f9b2bef0706315e0c",
            "f79921bbae40a577928b76d2fc3edc2a",
            "fccb3cdc9acc14a6e70a12f74560c026",
            "53e3a7161e428b65688f14b84d61c610",
            "d6c651ddcd97183b2e40bc464231c962",
            "500e75a036dc2d7d2fec5da1b71d36cc",
            "1ecfb463472ec9115b10c292ef8bc986",
            "e70611883d2760c8bbafb4acb29e3446",
            "0537fb40a68c18da59a35c2bfe1ca554",
            "185e65bc40581880c4f2c82958de8cfe",
            "8d317bdcf4aafcfc22149d77babee96d",
            "b056eb1587586b71e2da9acfe4fbd19e",
            "912d2b1c7b2826caf99687388d2e8f7c",
            "a1d33d0dfec820b41b54430b50e96b5c",
            "6f2268bd1d3d3ebaabb04d6b5d099425",
            "872488f88d1b2db54d55bc8bba2fad1b",
            "ccb0989662211f61edae2e26d58ea92f",
            "2823f4797102ce1a1aec05359cc16dd9",
            "f2201f5191c4e92cc5af043eebfd0946",
            "288cc0ff022877bd3df94bc9360b9c5d",
            "4ea06fbc83cdd0a06020c35d50e1e89a",
            "b7ee6f5f9aa5cd17ca1aea43ce848496",
            "86b122d4358357d834a87ce618a55de0",
            "4e0928de075538c593fbdabb0c5ef2c3",
            "c0f168ce8900fa56e57789e2a2f2c9d0",
            "7143d7fbadfa4693b9eec507d9d37443",
            "61b4a64be663682e8cb037d9719ad8cd",
            "df7f28ac89ca37bf1abd2f6c184fe1cf",
            "da8ce53cf0240070ce6c69c48cd588ee",
            "82489c9737cc245530c7a6ebef3753ec",
            "7c590f01490190db0ed02a5070e20f01",
            "35cf8659cfcb13224cbd47863a34fc58",
            "beb22fb694d513edcf5533cf006dfeae",
            "f0adc8838f4bdedde4ec2cfad0515589"
    };
    ListView listView;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.customListView);
        CustomBaseAdapter customerBaseAdapter = new CustomBaseAdapter(getApplicationContext(), fruitList, fruitImages, episodeDescription);
        listView.setAdapter(customerBaseAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {

            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            int epNum = i+1;


            editor.putString(EPISODE, String.valueOf(epNum));
            editor.putString(IMAGE, "2");                     //Osman
            editor.putString(LINK, episodeLink[i]);
            editor.apply();
            finish();
            Intent intent2 = new Intent(getApplicationContext(), PlayerActivity.class);
            startActivity(intent2);

            /*PopupMenu popup = new PopupMenu(MainActivity.this, view);
            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.sd:
                        //save osman episode info for player to get episode number, series name, link, quality
                        editor.putString("Quality","480");
                        editor.putString("Episode", String.valueOf(epNum));
                        editor.putString("Image", "2");                     //Osman
                        editor.putString("Link", episodeLink[i]);
                        editor.apply();
                        menuObj.active = true;

                        //exit this activity and go to player
                        finish();
                        Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.hd:
                        editor.putString("Quality","720");
                        editor.putString("Episode", String.valueOf(epNum));
                        editor.putString("Image", "2");                     //Osman
                        editor.putString("Link", episodeLink[i]);
                        editor.apply();
                        menuObj.active = true;

                        //exit this activity and go to player
                        finish();
                        Intent intent3 = new Intent(getApplicationContext(), PlayerActivity.class);
                        startActivity(intent3);
                        return true;
                    case R.id.fhd:
                        editor.putString("Quality","1080");
                        editor.putString("Episode", String.valueOf(epNum));
                        editor.putString("Image", "2");                     //Osman
                        editor.putString("Link", episodeLink[i]);
                        editor.apply();
                        menuObj.active = true;

                        //exit this activity and go to player
                        finish();
                        Intent intent2 = new Intent(getApplicationContext(), PlayerActivity.class);
                        startActivity(intent2);
                        return true;
                    default:
                        return false;
                }
            });
            // show menu
            popup.show();*/
        });
        //updateEpisodeProgress();



    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
        finish();
    }

    void CallPlayer(String url, String epName, int episodeNumber) {

        SharedPreferences info = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = info.edit();
        myEdit.putString(EPISODE, Integer.toString(episodeNumber));
        myEdit.putString(IMAGE, "2");
        myEdit.putString(LINK,url);
        myEdit.commit();

        title = epName;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.mxtech.videoplayer.ad");
        intent.setClassName("com.mxtech.videoplayer.ad", "com.mxtech.videoplayer.ad.ActivityScreen");
        Uri videoUri = Uri.parse(WEB_URL + url /*+ "&q=" + quality*/);
        intent.setDataAndType(videoUri, "application/x-mpegURL");
        //application/x-mpegURL
        intent.setPackage("com.mxtech.videoplayer.ad"); // com.mxtech.videoplayer.pro
        //intent.putExtra("return_result", true);
        intent.putExtra("title", epName);
        startActivityForResult(intent, 0);
    }


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)  // -1 RESULT_OK : Playback was completed or stopped by user request.
            //Activity.RESULT_CANCELED: User canceled before starting any playback.
            //RESULT_ERROR (=Activity.RESULT_FIRST_USER): Last playback was ended with an error.

            if (data.getAction().equals("com.mxtech.intent.result.VIEW")) {
                pos = data.getIntExtra("position", -1); // Last playback position in milliseconds. This extra will not exist if playback is completed.
                dur = data.getIntExtra("duration", -1); // Duration of last played video in milliseconds. This extra will not exist if playback is completed.
                String cause = data.getStringExtra("end_by"); //  Indicates reason of activity closure.
                Toast.makeText(this, pos, Toast.LENGTH_SHORT).show();
                /*SharedPreferences info = getSharedPreferences("info", MODE_PRIVATE);
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

