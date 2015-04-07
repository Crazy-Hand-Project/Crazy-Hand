package com;

import java.util.ArrayList;
import java.util.HashMap;

import com.SpecialAttributeIndex.SpecialMoveAttribute;

public class SpecialMovesList{
	
	ArrayList<SubAction> specialMoves = new ArrayList<SubAction>();
	public static HashMap<Character,SubAction[]> movesToCharacterMap = new HashMap<Character,SubAction[]>();
	public static HashMap<Character,SpecialMoveAttribute[]> specialAttributesToCharacterMap = new HashMap<Character, SpecialMoveAttribute[]>();
	//Used during debugging for easily swapping out values to explore character files within crazy hand.
	public static SpecialAttributeIndex attributeInstance;
	
	public static void load(){
		movesToCharacterMap.clear();
		//***Comment out these lines if they are causing long load times***
		specialAttributesToCharacterMap.clear();
		attributeInstance = new SpecialAttributeIndex();
		//***Comment out these lines if they are causing long load times***
		SubAction[] none = {new SubAction(0x125,"No special Moves! DO NOT EDIT")};
		
		movesToCharacterMap.put(Character.characters[0],bowserSpecialMoves);
		movesToCharacterMap.put(Character.characters[1],ganonfalconSpecialMoves);
		movesToCharacterMap.put(Character.characters[2],marioSpecialMoves);
		movesToCharacterMap.put(Character.characters[3],donkeyKongSpecialMoves);
		movesToCharacterMap.put(Character.characters[4],spacieSpecialMoves);
		movesToCharacterMap.put(Character.characters[5],spacieSpecialMoves);
		movesToCharacterMap.put(Character.characters[6],gnwSpecialMoves);
		movesToCharacterMap.put(Character.characters[7],ganonfalconSpecialMoves);
		movesToCharacterMap.put(Character.characters[8],popoNanaSpecialMoves);
		movesToCharacterMap.put(Character.characters[9],popoNanaSpecialMoves);
		movesToCharacterMap.put(Character.characters[10],jigglypuffSpecialMoves);
		movesToCharacterMap.put(Character.characters[11],placeholder);
		movesToCharacterMap.put(Character.characters[12],luigiSpecialMoves);
		movesToCharacterMap.put(Character.characters[13],linkSpecialMoves);
		movesToCharacterMap.put(Character.characters[14],marioSpecialMoves);
		movesToCharacterMap.put(Character.characters[15],feSwordsmanSpecialMoves);
		movesToCharacterMap.put(Character.characters[16],mewtwoSpecialMoves);
		movesToCharacterMap.put(Character.characters[17],nessSpecialMoves);
		movesToCharacterMap.put(Character.characters[18],peachSpecialMoves);
		movesToCharacterMap.put(Character.characters[19],pikaSpecialMoves);
		movesToCharacterMap.put(Character.characters[20],pikaSpecialMoves);
		movesToCharacterMap.put(Character.characters[21],feSwordsmanSpecialMoves);
		movesToCharacterMap.put(Character.characters[22],samusSpecialMoves);
		movesToCharacterMap.put(Character.characters[23],shiekSpecialMoves);
		movesToCharacterMap.put(Character.characters[24],yoshiSpecialMoves);
		movesToCharacterMap.put(Character.characters[25],linkSpecialMoves);
		movesToCharacterMap.put(Character.characters[26],zeldaSpecialMoves);
		movesToCharacterMap.put(Character.characters[27],none);
		movesToCharacterMap.put(Character.characters[28],none);
		movesToCharacterMap.put(Character.characters[29],bowserSpecialMoves);
		movesToCharacterMap.put(Character.characters[30],none);
		movesToCharacterMap.put(Character.characters[31],none);
		movesToCharacterMap.put(Character.characters[32],none);		
	}
	
	public static SubAction[] placeholder = {
		new SubAction(0x127, "Work in progress! (DO NOT EDIT)")
	};
	
	public static SubAction[] bowserSpecialMoves = {
		new SubAction(0x127, "(Ground)Neutral B Start"),
		new SubAction(0x128, "(Ground)Neutral B Loop"),
		new SubAction(0x129, "(Ground)Neutral B End"),
		new SubAction(0x12A, "(Air)Neutral B Start"),
		new SubAction(0x12B, "(Air)Neutral B Loop"),
		new SubAction(0x12C, "(Air)Neutral B End"),
		new SubAction(0x12D, "(Ground)Side B Start"),
		new SubAction(0x12E, "(Ground)Side B Hit"),//One is forward, other is behind?
		new SubAction(0x12F, "(Ground)Side B Hit(2)"),//One is forward, other is behind?
		new SubAction(0x130, "(Ground)Side B End Forward"),
		new SubAction(0x131, "(Ground)Side B End Backward"),
		new SubAction(0x132, "(Air)Side B Start"),
		new SubAction(0x133, "(Air)Side B Hit"),//One is forward, other is behind?
		new SubAction(0x134, "(Air)Side B Hit(2)"),//One is forward, other is behind?
		new SubAction(0x135, "(Air)Side B End Forward"),
		new SubAction(0x136, "(Air)Side B End Backward"),
		new SubAction(0x137, "(Ground)Up B"),
		new SubAction(0x138, "(Air)Up B"),
		new SubAction(0x139, "(Ground)Down B"),
		new SubAction(0x13A, "(Air)Down B"),
		new SubAction(0x13B, "Down B Landing")
	};
	
	public static SubAction[] ganonfalconSpecialMoves = {
		new SubAction(0x12D, "(Ground)Neutral B"),
		new SubAction(0x12E, "(Air)Neutral B"),
		new SubAction(0x12F, "(Ground)Side B Start"),
		new SubAction(0x130, "(Ground)Side B"),
		new SubAction(0x131, "(Air)Side B Start"),
		new SubAction(0x132, "(Air)Side B"),
		new SubAction(0x133, "(Ground)Up B"),
		new SubAction(0x134, "(Air)Up B"),
		new SubAction(0x135, "Up B Hold"),
		new SubAction(0x136, "Up B Release"),
		new SubAction(0x137, "(Ground)Down B"),
		new SubAction(0x138, "(Ground)Down B End"),
		new SubAction(0x139, "(Air)Down B"),
		new SubAction(0x13A, "(Air)Down B End"),
		new SubAction(0x13B, "(Ground)Down B End In-Air"),
		new SubAction(0x13C, "(Air)Down B End In-Air"),
		
		//Data isn't being read correctly for this pointer
		//It may have something to do with FileIO and how it handles
		//the offset start/end of each character, and the data for this
		//may be outside the bounds set in Character.
		//new SubAction(0x13D, "b'SpecialHiThrow"),
	};
	
	public static SubAction[] donkeyKongSpecialMoves = {
		new SubAction(0x13F, "(Ground)Neutral B Start"),
		new SubAction(0x140, "(Ground)Neutral B Loop"),
		new SubAction(0x141, "(Ground)Neutral B Cancel"),
		new SubAction(0x142, "(Ground)Neutral B"),
		new SubAction(0x143, "(Ground)Neutral B(2)"),
		new SubAction(0x144, "(Air)Neutral B Start"),
		new SubAction(0x145, "(Air)Neutral B Loop"),
		new SubAction(0x146, "(Air)Neutral B Cancel"),
		new SubAction(0x147, "(Air)Neutral B"),
		new SubAction(0x148, "(Air)Neutral B(2)"),
		new SubAction(0x149, "(Ground)Side B"),
		new SubAction(0x14A, "(Air)Side B"),
		new SubAction(0x14B, "(Ground)Up B"),
		new SubAction(0x14C, "(Air)Up B"),
		new SubAction(0x14D, "Down B Start"),
		new SubAction(0x14E, "Down B Loop"),
		new SubAction(0x14F, "Down B End"),
		new SubAction(0x150, "Down B End(2)"),
	};
	
	public static SubAction[] spacieSpecialMoves = {
		new SubAction(0x127, "(Ground)Neutral B Start"),
		new SubAction(0x128, "(Ground)Neutral B Loop"),
		new SubAction(0x129, "(Ground)Neutral B End"),
		new SubAction(0x12A, "(Air)Neutral B Start"),
		new SubAction(0x12B, "(Air)Neutral B Loop"),
		new SubAction(0x12C, "(Air)Neutral B End"),
		new SubAction(0x12D, "(Ground)Side B Start"),
		new SubAction(0x12E, "(Ground)Side B"),
		new SubAction(0x12F, "(Ground)Side B End"),
		new SubAction(0x130, "(Air)Side B Start"),
		new SubAction(0x131, "(Air)Side B"),
		new SubAction(0x132, "(Air)Side B End"),
		new SubAction(0x133, "(Ground)Up B Hold"),
		new SubAction(0x134, "(Air)Up B Hold"),
		new SubAction(0x135, "Up B"),
		new SubAction(0x136, "Up B Landing"),
		new SubAction(0x137, "Up B Fall"),
		new SubAction(0x138, "Up B Bound"),
		new SubAction(0x139, "(Ground)Down B Start"),
		new SubAction(0x13A, "(Ground)Down B Loop"),
		new SubAction(0x13B, "(Ground)Down B Hit"),
		new SubAction(0x13C, "(Ground)Down B End"),
		new SubAction(0x13D, "(Air)Down B Start"),
		new SubAction(0x13E, "(Air)Down B Loop"),
		new SubAction(0x13F, "(Air)Down B Hit"),
		new SubAction(0x140, "(Air)Down B End"),
		
		//There's more entries after this, starting with AppealS
		//It might be a taunt?
		
		//new SubAction(0x141, "b'Special"),
	};
	
	public static SubAction[] gnwSpecialMoves = {
		new SubAction(0x127, "(Ground)Neutral B"),
		new SubAction(0x128, "(Air)Neutral B"),
		new SubAction(0x129, "(Ground)Side B (1)"),
		new SubAction(0x12A, "(Ground)Side B (2)"),
		new SubAction(0x12B, "(Ground)Side B (3)"),
		new SubAction(0x12C, "(Ground)Side B (4)"),
		new SubAction(0x12D, "(Ground)Side B (5)"),
		new SubAction(0x12E, "(Ground)Side B (6)"),
		new SubAction(0x12F, "(Ground)Side B (7)"),
		new SubAction(0x130, "(Ground)Side B (8)"),
		new SubAction(0x131, "(Ground)Side B (9)"),
		new SubAction(0x132, "(Air)Side B (1)"),
		new SubAction(0x133, "(Air)Side B (2)"),
		new SubAction(0x134, "(Air)Side B (3)"),
		new SubAction(0x135, "(Air)Side B (4)"),
		new SubAction(0x136, "(Air)Side B (5)"),
		new SubAction(0x137, "(Air)Side B (6)"),
		new SubAction(0x138, "(Air)Side B (7)"),
		new SubAction(0x139, "(Air)Side B (8)"),
		new SubAction(0x13A, "(Air)Side B (9)"),
		new SubAction(0x13B, "(Ground)Up B"),
		new SubAction(0x13C, "(Air)Up B"),
		new SubAction(0x13D, "(Ground)Down B"),
		new SubAction(0x13E, "(Ground)Down B Absorb"),
		new SubAction(0x13F, "(Ground)Down B Shoot"),
		new SubAction(0x140, "(Air)Down B"),
		new SubAction(0x141, "(Air)Down B Absorb"),
		new SubAction(0x142, "(Air)Down B Shoot"),
	};
	
	public static SubAction[] popoNanaSpecialMoves = {
		new SubAction(0x127, "(Ground)Neutral B"),
		new SubAction(0x128, "(Air)Neutral B"),
		new SubAction(0x129, "(Ground)Side B (1)"),
		new SubAction(0x12A, "(Ground)Side B (2)"),
		new SubAction(0x12B, "(Air)Side B (1)"),
		new SubAction(0x12C, "(Air)Side B (2)"),
		new SubAction(0x12D, "(Ground)Up B Start"),
		new SubAction(0x12E, "(Ground)Up B Throw(1)"),
		new SubAction(0x12F, "(Ground)Up B Throw(2)"),
		new SubAction(0x130, "(Ground)Up B Throw(3)"),
		new SubAction(0x131, "(Ground)Up B Throw(4)"),
		new SubAction(0x132, "(Air)Up B Start"),
		new SubAction(0x133, "(Air)Up B Throw(1)"),
		new SubAction(0x134, "(Air)Up B Throw(2)"),
		new SubAction(0x135, "(Air)Up B Throw(3)"),
		new SubAction(0x136, "(Air)Up B Throw(4)"),
		new SubAction(0x137, "(Ground)Down B"),
		new SubAction(0x138, "(Air)Down B"),
		
		//More unnamed entries below specialAirLw, I'd assume it has something to do
		//with syncing Nana to Popo or vice versa
	};
	
	public static SubAction[] jigglypuffSpecialMoves = {
		new SubAction(0x12C, "(Ground)Neutral B Start (R)"),
		new SubAction(0x12D, "(Ground)Neutral B Start (L)"),
		new SubAction(0x12E, "(Ground)Neutral B(1)"),
		new SubAction(0x12F, "(Ground)Neutral B(2)"),
		new SubAction(0x130, "(Ground)Neutral B(3)"),
		new SubAction(0x131, "(Ground)Neutral B(4)"),
		new SubAction(0x132, "(Ground)Neutral B End (R)"),
		new SubAction(0x133, "(Ground)Neutral B End (L)"),
		
		new SubAction(0x12C, "(Air)Neutral B Start (R)"),
		new SubAction(0x12D, "(Air)Neutral B Start (L)"),
		new SubAction(0x12E, "(Air)Neutral B(1)"),
		new SubAction(0x12F, "(Air)Neutral B(2)"),
		new SubAction(0x130, "(Air)Neutral B(3)"),
		new SubAction(0x131, "(Air)Neutral B(4)"),
		new SubAction(0x132, "(Air)Neutral B End (R)"),
		new SubAction(0x133, "(Air)Neutral B End (L)"),
		new SubAction(0x13C, "Neutral B"),
		new SubAction(0x13D, "(Ground)Side B"),
		new SubAction(0x13E, "(Air)Side B"),
		new SubAction(0x13F, "(Ground)Up B (L)"),
		new SubAction(0x140, "(Air)Up B (L)"),
		new SubAction(0x141, "(Ground)Up B (R)"),
		new SubAction(0x142, "(Air)Up B (R)"),
		new SubAction(0x143, "(Ground)Down B (L)"),
		new SubAction(0x144, "(Air)Down B (L)"),
		new SubAction(0x145, "(Ground)Down B (R)"),
		new SubAction(0x146, "(Air)Down B (R)"),
	};
	
	//Kirby's special moves are a mess.
	//Some entries have more data in this program than they do in master hand and vice versa.
	//On top of that, everything starts to desync heavily around jigg's special moves.
	public static SubAction[] kirbySpecialMoves = {
		new SubAction(0x131, "b'SpecialN"),
		new SubAction(0x132, "b'SpecialNLoop"),
		new SubAction(0x133, "b'SpecialNEnd"),
		new SubAction(0x134, "b'SpecialNLoop(2)"),
		new SubAction(0x135, "b'Eat"),
		new SubAction(0x136, "b'EatWait"),
		new SubAction(0x137, "b'EatWalkSlow"),
		new SubAction(0x138, "b'EatWalkMiddle"),
		new SubAction(0x139, "b'EatWalkFast"),
		new SubAction(0x13A, "b'EatJump1"),
		new SubAction(0x13B, "b'EatJump2"),
		new SubAction(0x13C, "b'EatLanding"),
		new SubAction(0x13D, "b'EatTurn"),
		new SubAction(0x13E, "b'SpecialNDrink"),
		new SubAction(0x13F, "b'SpecialNSpit"),
		new SubAction(0x140, "b'SpecialN(2)"),
		new SubAction(0x141, "b'SpecialNLoop(3)"),
		new SubAction(0x142, "b'SpecialS"),
		new SubAction(0x143, "b'SpecialAirS"),
		new SubAction(0x144, "b'SpecialHi1"),
		new SubAction(0x145, "b'SpecialHi2"),
		new SubAction(0x146, "b'SpecialHi3"),
		new SubAction(0x147, "b'SpecialHi4"),
		new SubAction(0x148, "b'SpecialAirHi1"),
		new SubAction(0x149, "b'SpecialAirHi2"),
		new SubAction(0x14A, "b'SpecialAirHi3"),
		new SubAction(0x14B, "b'SpecialAirHi4"),
		new SubAction(0x14C, "b'SpecialLw1"),
		new SubAction(0x14D, "b'SpecialLw2"),
		new SubAction(0x14E, "b'SpecialLw2(2)"),
		new SubAction(0x14F, "b'SpecialAirLw1"),
		new SubAction(0x150, "b'SpecialAirLw2"),
		new SubAction(0x151, "b'SpecialAirLw2(2)"),
		new SubAction(0x152, "b'MrSpecialN"),
		new SubAction(0x153, "b'MrSpecialAirN"),
		new SubAction(0x154, "b'LkSpecialNStart"),
		new SubAction(0x155, "b'LkSpeciaNLoop"),
		new SubAction(0x156, "b'LkSpecialNEnd"),
		new SubAction(0x157, "b'LkSpecialAirNStart"),
		new SubAction(0x158, "b'LkSpecialAirNLoop"),
		new SubAction(0x159, "b'LkSpecialAirNEnd"),
		new SubAction(0x15A, "b'SsSpecialNStart"),
		new SubAction(0x15B, "b'SsSpecialNHold"),
		new SubAction(0x15C, "b'SsSpecialNCancel"),
		new SubAction(0x15D, "b'SsSpecialN"),
		new SubAction(0x15E, "b'SsSpecialAirNStart"),
		new SubAction(0x15F, "b'SsSpecialAirN"),
		new SubAction(0x160, "b'YsSpecialAirN1"),
		new SubAction(0x161, "b'YsSpecialAirN1(2)"),
		new SubAction(0x162, "b'YsSpecialAirN2"),
		new SubAction(0x163, "b'FxSpecialNStart"),
		new SubAction(0x164, "b'FxSpecialNLoop"),
		new SubAction(0x165, "b'FxSpecialNEnd"),
		new SubAction(0x166, "b'FxSpecialAirNStart"),
		new SubAction(0x167, "b'FxSpecialAirNLoop"),
		new SubAction(0x168, "b'FxSpecialAirNEnd"),
		new SubAction(0x169, "b'PkSpecialN"),
		new SubAction(0x16A, "b'PkSpecialAirN"),
		new SubAction(0x16B, "b'LgSpecialN"),
		new SubAction(0x16C, "b'LgSpecialAirN"),
		new SubAction(0x16D, "b'CaSpecialN"),
		new SubAction(0x16E, "b'CaSpecialAirN"),
		new SubAction(0x16F, "b'NsSpecialNStart"),
		new SubAction(0x170, "b'NsSpecialNHold"),
		new SubAction(0x171, "b'NsSpecialNHold(2)"),
		new SubAction(0x172, "b'NsSpecialNEnd"),
		new SubAction(0x173, "b'NsSpecialAirNStart"),
		new SubAction(0x174, "b'NsSpecialAirNHold"),
		new SubAction(0x175, "b'NsSpecialAirNHold(2)"),
		new SubAction(0x176, "b'NsSpecialAirNEnd"),
		new SubAction(0x177, "b'KpSpecialNStart"),
		new SubAction(0x178, "b'KpSpecialN"),
		new SubAction(0x179, "b'KpSpecialNEnd"),
		new SubAction(0x17A, "b'KpSpecialAirNStart"),
		new SubAction(0x17B, "b'KpSpecialAirN"),
		new SubAction(0x17C, "b'KpSpecialAirNEnd"),
		new SubAction(0x17D, "b'PeSpecialLw"),
		new SubAction(0x17E, "b'PeSpecialLwHit"),
		new SubAction(0x17F, "b'PeSpecialAirLw"),
		new SubAction(0x180, "b'PeSpecialAirLwHit"),
		new SubAction(0x181, "b'PpSpecialN"),
		new SubAction(0x182, "b'PpSpecialAirN"),
		new SubAction(0x183, "b'DkSpecialNStart"),
		new SubAction(0x184, "b'DkSpecialNLoop"),
		new SubAction(0x185, "b'DkSpecialNCancel"),
		new SubAction(0x186, "b'DkSpecialN"),
		new SubAction(0x187, "b'DkSpecialN(2)"),
		new SubAction(0x188, "b'DkSpecialAirNStart"),
		new SubAction(0x189, "b'DkSpecialAirNLoop"),
		new SubAction(0x18A, "b'DkSpecialAirNCancel"),
		new SubAction(0x18B, "b'DkSpecialAirN"),
		new SubAction(0x18C, "b'DkSpecialAirN(2)"),
		new SubAction(0x18D, "b'ZdSpecialN"),
		new SubAction(0x18E, "b'ZdSpecialAirN"),
		new SubAction(0x18F, "b'SkSpecialNStart"),
		new SubAction(0x190, "b'SkSpecialNLoop"),
		new SubAction(0x191, "b'SkSpecialNCancel"),
		new SubAction(0x192, "b'SkSpecialNEnd"),
		new SubAction(0x193, "b'SkSpecialAirNStart"),
		new SubAction(0x194, "b'SkSpecialAirNLoop"),
		new SubAction(0x195, "b'SkSpecialAirNEnd"),
		new SubAction(0x196, "b'PrSpecialNStartR"),
		new SubAction(0x197, "b'PrSpecialNStartL"),
		new SubAction(0x198, "b'PrSpecialN"),
		new SubAction(0x199, "b'PrSpecialN(2)"),
		new SubAction(0x19A, "b'PrSpecialN(3)"),
		new SubAction(0x19B, "b'PrSpecialN(4)"),
		new SubAction(0x19C, "b'PrSpecialNEndR"),
		new SubAction(0x19D, "b'PrSpecialNEndL"),
		new SubAction(0x19E, "b'PrSpecialAirNStartR"),
		new SubAction(0x19F, "b'PrSpecialAirNStartL"),
		new SubAction(0x1A0, "b'PrSpecialN(5)"),
		new SubAction(0x1A1, "b'PrSpecialN(6)"),
		new SubAction(0x1A2, "b'PrSpecialN(7)"),
		new SubAction(0x1A3, "b'PrSpecialAirNEndR"),
		new SubAction(0x1A4, "b'PrSpecialAirNEndR(2)"),
		new SubAction(0x1A5, "b'PrSpecialN(8)"),
		new SubAction(0x1A6, "b'MsSpecialNStart"),
		new SubAction(0x1A7, "b'MsSpecialNLoop"),
		new SubAction(0x1A8, "b'MsSpecialNEnd"),
		new SubAction(0x1A9, "b'MsSpecialNEnd(2)"),
		new SubAction(0x1AA, "b'MsSpecialAirNStart"),
		new SubAction(0x1AB, "b'MsSpecialAirNLoop"),
		new SubAction(0x1AC, "b'MsSpecialAirNEnd"),
		new SubAction(0x1AD, "b'MsSpecialAirNEnd(2)"),
		new SubAction(0x1AE, "b'MtSpecialNStart"),
		new SubAction(0x1AF, "b'MtSpecialNLoop"),
		new SubAction(0x1B0, "b'MtSpecialNLoop(2)"),
		new SubAction(0x1B1, "b'"),
		new SubAction(0x1B2, "b'"),
		new SubAction(0x1B3, "b'"),
		new SubAction(0x1B4, "b'"),
		new SubAction(0x1B5, "b'"),
		new SubAction(0x1B6, "b'"),
		new SubAction(0x1B7, "b'"),
		new SubAction(0x1B8, "b'"),
		new SubAction(0x1B9, "b'"),
		new SubAction(0x1BA, "b'"),
		new SubAction(0x1BB, "b'"),
		new SubAction(0x1BC, "b'"),
		new SubAction(0x1BD, "b'"),
		new SubAction(0x1BE, "b'"),
		new SubAction(0x1BF, "b'"),
	};
	
	public static SubAction[] luigiSpecialMoves = {
		new SubAction(0x127, "(Ground)Neutral B"),
		new SubAction(0x128, "(Air)Neutral B"),
		new SubAction(0x129, "(Ground)Side B Start"),
		new SubAction(0x12A, "(Ground)Side B Charge"),
		new SubAction(0x12B, "(Ground)Side B(1)"),
		new SubAction(0x12C, "(Ground)Side B(2)"),
		new SubAction(0x12D, "(Ground)Side B(3)"),
		new SubAction(0x12E, "(Ground)Side B End"),
		new SubAction(0x12F, "(Air)Side B Start"),
		new SubAction(0x130, "(Air)Side B Charge"),
		//Most likely, these two side B entries aren't air versions of the move, but are entries for misfire.
		//We can re-label them later once we're sure.
		new SubAction(0x131, "(Air)Side B(1)"),
		new SubAction(0x132, "(Air)Side B(2)"),
		new SubAction(0x133, "(Air)Side B End"),
		new SubAction(0x134, "(Ground)Up B"),
		new SubAction(0x135, "(Air)Up B"),
		new SubAction(0x136, "(Ground)Down B"),
		new SubAction(0x137, "(Air)Down B"),
	};
	
	public static SubAction[] linkSpecialMoves = {
		new SubAction(0x128, "(Ground)Neutral B Start"),
		new SubAction(0x129, "(Ground)Neutral B Charge"),
		new SubAction(0x12A, "(Ground)Neutral B End"),
		new SubAction(0x12B, "(Air)Neutral B Start"),
		new SubAction(0x12C, "(Air)Neutral B Charge"),
		new SubAction(0x12D, "(Air)Neutral B End"),
		new SubAction(0x12E, "(Ground)Side B Throw"),
		new SubAction(0x12F, "(Ground)Side B Catch"),
		new SubAction(0x130, "(Ground)Side B Throw(Angled)"),
		new SubAction(0x131, "(Air)Side B Throw"),
		new SubAction(0x132, "(Air)Side B Catch"),
		new SubAction(0x133, "(Air)Side B Throw(Angled)"),
		new SubAction(0x134, "(Ground)Up B"),
		new SubAction(0x135, "(Air)Up B"),
		new SubAction(0x136, "(Ground)Down B"),
		new SubAction(0x137, "(Ground)Down B"),
		//Two more entries below SpecialAirLw, AirCatch and AirCatchHit.
		//I'd assume that's links zair.
		//There's also an entry above SpecialNStart, AttackS42.
		//Its entries look similar to down tilt, and all its hitboxes launch at angle 65
		//I'd assume this is the spike hitbox for link's down tilt.
		//Maybe consider creating a new list for misc. subactions for cases like this.
	};
	
	public static SubAction[] marioSpecialMoves = {
		//These comments are from when I was trying to figure out a mathematical
		//algorithm for finding a character's special moves without manually entering the offsets
		//I doubt they're still useful, but I'm leaving them here just in case.
		
		//0x50 is throwing items?
		//0x51 is lightthrowhi?
		//0x52 is itemHammerMove?
		//0x76 is the third occurance of swing4 (46 moves down from airattacklw)
		//0x95 is ItemScopeStart (77 moves down from airattacklw)
		//0xBF is DownBoundD (10 moves from PassiveStandB)
		//0xC7 is PassiveStandF (98 moves down from airattacklw)
		//0xC8 is Passive? (7 moves down from 0xC7)
		//0xC9 is PassiveStandB? (2 moves down from 0xC8)
		//0xD0 is FuraSleepEnd (6 moves down from 0xC9)
		//0xD8 is CliffCatch (7 moves down from 0xD0)
		//0x127 is SpecialN!
		new SubAction(0x127, "(Ground)Neutral B"),
		new SubAction(0x128, "(Air)Neutral B"),
		new SubAction(0x129, "(Ground)Side B"),
		new SubAction(0x12a, "(Air)Side B"),
		new SubAction(0x12b, "(Ground)Up B"),
		new SubAction(0x12c, "(Air)Up B"),
		new SubAction(0x12d, "(Ground)Down B"),
		new SubAction(0x12e, "(Air)Down B")
	};
	
	public static SubAction[] feSwordsmanSpecialMoves = {
		new SubAction(0x127, "Neutral B Start"),
		new SubAction(0x128, "Neutral B Hold"),
		new SubAction(0x129, "Neutral B End"),
		new SubAction(0x12A, "Neutral B End(2)"),
		new SubAction(0x12B, "(Air)Neutral B Start"),
		new SubAction(0x12C, "(Air)Neutral B Hold"),
		new SubAction(0x12D, "(Air)Neutral B End"),
		new SubAction(0x12E, "(Air)Neutral B End(2)"),
		new SubAction(0x12F, "Side B Start"),
		new SubAction(0x130, "(Step 1)Side B (gcsur)"),
		new SubAction(0x131, "(Step 1)Side B (gcsdr)"),
		new SubAction(0x132, "(Step 2)Side B (gcsur)"),
		new SubAction(0x133, "(Step 2)Side B (gcsr)"),
		new SubAction(0x134, "(Step 2)Side B (gcsdr)"),
		new SubAction(0x135, "(Step 3)Side B (gcsur)"),
		new SubAction(0x136, "(Step 3)Side B (gcsr)"),
		new SubAction(0x137, "(Step 3)Side B (gcsdr)"),
		new SubAction(0x138, "(Air)Side B Start"),
		new SubAction(0x139, "(Air, Step 1)Side B (gcsur)"),
		new SubAction(0x13A, "(Air, Step 1)Side B (gcsdr)"),
		new SubAction(0x13B, "(Air, Step 2)Side B (gcsur)"),
		new SubAction(0x13C, "(Air, Step 2)Side B (gcsr)"),
		new SubAction(0x13D, "(Air, Step 2)Side B (gcsdr)"),
		new SubAction(0x13E, "(Air, Step 3)Side B (gcsur)"),
		new SubAction(0x13F, "(Air, Step 3)Side B (gcsr)"),
		new SubAction(0x140, "(Air, Step 3)Side B (gcsdr)"),
		new SubAction(0x141, "(Ground)Up B"),
		new SubAction(0x142, "(Air)Up B"),
		new SubAction(0x143, "(Ground)Down B"),
		new SubAction(0x144, "(Ground)Down B Counter"),
		new SubAction(0x145, "(Air)Down B"),
		new SubAction(0x146, "(Air)Down B Counter"),
	};
	
	
	public static SubAction[] mewtwoSpecialMoves = {
		new SubAction(0x127, "(Ground)Neutral B Start"),
		new SubAction(0x128, "b'SpecialNLoop"),
		new SubAction(0x129, "b'SpecialNLoop(2)"),
		new SubAction(0x12A, "b'SpecialNCancel"),
		new SubAction(0x12B, "b'SpecialNEnd"),
		new SubAction(0x12C, "b'SpecialAirNStart"),
		new SubAction(0x12E, "b'SpecialAirNLoop"),
		new SubAction(0x12D, "b'SpecialAirNLoop(2)"),
		new SubAction(0x12F, "b'SpecialAirNCancel"),
		new SubAction(0x130, "b'SpecialAirNEnd"),
		new SubAction(0x131, "b'SpecialS"),
		new SubAction(0x132, "b'SpecialAirS"),
		new SubAction(0x133, "b'SpecialHiStart"),
		new SubAction(0x134, "b'SpecialHi"),
		new SubAction(0x135, "b'SpecialHiLost"),
		new SubAction(0x136, "b'SpecialAirHiStart"),
		new SubAction(0x137, "b'SpecialAirHi"),
		new SubAction(0x138, "b'SpecialLw"),
		new SubAction(0x139, "b'SpecialAirLw"),
	};
	
	public static SubAction[] nessSpecialMoves = {
		new SubAction(0x12B, "SpecialNStart"),
		new SubAction(0x12C, "SpecialNHold"),
		new SubAction(0x12D, "SpecialNHold(2)"),
		new SubAction(0x12E, "SpecialNEnd"),
		new SubAction(0x12F, "SpecialAirNStart"),
		new SubAction(0x130, "SpecialAirNHold"),
		new SubAction(0x131, "SpecialAirNHold(2)"),
		new SubAction(0x132, "SpecialAirNEnd"),
		new SubAction(0x133, "SpecialS"),
		new SubAction(0x134, "SpecialAirS"),
		new SubAction(0x135, "SpecialHiStart"),
		new SubAction(0x136, "SpecialHiHold"),
		new SubAction(0x137, "SpecialHiEnd"),
		new SubAction(0x138, "SpecialHi"),
		new SubAction(0x139, "SpecialAirHiStart"),
		new SubAction(0x13A, "SpecialAirHiHold"),
		new SubAction(0x13B, "SpecialAirHiEnd"),
		new SubAction(0x13C, "SpecialHi(2)"),
		new SubAction(0x13D, "DamageFall"),
		new SubAction(0x13E, "SpecialLwStart"),
		new SubAction(0x13F, "SpecialLwHold"),
		new SubAction(0x140, "SpecialLwHit"),
		new SubAction(0x141, "SpecialLwEnd"),
		new SubAction(0x142, "SpecialAirLwStart"),
		new SubAction(0x143, "SpecialAirLwHold"),
		new SubAction(0x144, "SpecialAirLwHit"),
		new SubAction(0x145, "SpecialAirLwEnd"),
	};
	
	public static SubAction[] peachSpecialMoves = {
		new SubAction(0x12D, "Down B"),
		new SubAction(0x12E, "(Ground)Side B Start"),
		new SubAction(0x12F, "(Ground)Side B Miss"),
		new SubAction(0x130, "Side B Hit"),
		new SubAction(0x131, "(Air)Side B Start"),
		new SubAction(0x132, "(Air)Side B Miss"),
		new SubAction(0x133, "Side B hit wall(?)"),
		new SubAction(0x134, "(Ground)Up B Start"),
		new SubAction(0x135, "(Ground)Up B End"),
		new SubAction(0x136, "(Air)Up B Start"),
		new SubAction(0x137, "(Air)Up B End"),
		new SubAction(0x138, "(Ground)Neutral B"),
		new SubAction(0x139, "(Ground)Neutral B Counter"),
		new SubAction(0x13A, "(Air)Neutral B"),
		new SubAction(0x13B, "(Air)Neutral B Counter"),
		new SubAction(0x13C, "Open Parasol"),
		new SubAction(0x13D, "Close Parasol"),
	};
	
	public static SubAction[] pikaSpecialMoves = {
		new SubAction(0x127, "SpecialN"),
		new SubAction(0x128, "SpecialAirN"),
		new SubAction(0x129, "SpecialSStart"),
		new SubAction(0x12A, "SpecialSHold"),
		new SubAction(0x12B, "SpecialS"),
		new SubAction(0x12C, "SpecialS(2)"),
		new SubAction(0x12D, "SpecialSEnd"),
		new SubAction(0x12E, "SpecialAirSStart"),
		new SubAction(0x12F, "SpecialAirSHold"),
		new SubAction(0x130, "(Air)SpecialS"),
		new SubAction(0x131, "SpecialAirSEnd"),
		new SubAction(0x132, "SpecialHiStart"),
		new SubAction(0x133, "SpecialHiStart(2)"),
		new SubAction(0x134, "SpecialHiEnd"),
		new SubAction(0x135, "SpecialAirHiStart"),
		new SubAction(0x136, "SpecialAirHiStart(2)"),
		new SubAction(0x137, "SpecialAirHiEnd"),
		new SubAction(0x138, "SpecialLwStart"),
		new SubAction(0x139, "SpecialLwLoop"),
		new SubAction(0x13A, "SpecialLwLoop(2)"),
		new SubAction(0x13B, "SpecialLwEnd"),
		new SubAction(0x13C, "SpecialAirLwStart"),
		new SubAction(0x13D, "SpecialAirLwLoop"),
		new SubAction(0x13E, "SpecialAirLwLoop(2)"),
		new SubAction(0x13F, "SpecialAirLwEnd"),
	};
	
	public static SubAction[] samusSpecialMoves = {
		new SubAction(0x127, "SpecialLw"),
		new SubAction(0x128, "SpecialAirLw"),
		new SubAction(0x129, "SpecialNStart"),
		new SubAction(0x12A, "SpecialNHold"),
		new SubAction(0x12B, "SpecialNCancel"),
		new SubAction(0x12C, "SpecialN"),
		new SubAction(0x12D, "SpecialAirNStart"),
		new SubAction(0x12E, "SpecialAirN"),
		new SubAction(0x12F, "(Ground)Homing Rocket(?)"),
		new SubAction(0x130, "(Ground)Hard Rocket(?)"),
		new SubAction(0x131, "(Air)Homing Rocket(?)"),
		new SubAction(0x132, "(Air)Hard Rocket(?)"),
		new SubAction(0x133, "(Ground)Screw Attack"),
		new SubAction(0x134, "(Air)Screw Attack"),
		new SubAction(0x135, "(Ground)Down-B"),
		new SubAction(0x136, "(Air)Down-B"),
		//Two more entries below SpecialAirLw, AirCatch and AirCatchHit.
		//I'd assume that's zair.
		//Maybe consider creating a new list for misc. subactions for cases like this.
	};
	
	public static SubAction[] shiekSpecialMoves = {
		new SubAction(0x127, "(Ground)Needle Charge Start"),
		new SubAction(0x128, "(Ground)Needle Charge"),
		new SubAction(0x129, "(Ground)Needle Charge Cancel"),
		new SubAction(0x12A, "(Ground)Shoot Needles(?)"),
		new SubAction(0x12B, "(Air)Needle Charge Start"),
		new SubAction(0x12C, "(Air)Needle Charge"),
		new SubAction(0x12D, "(Air)Needle Charge Cancel"),
		new SubAction(0x12E, "(Air)Shoot Needles(?)"),
		new SubAction(0x12F, "(Ground)Whip Start"),
		new SubAction(0x130, "(Ground)Whip End"),
		new SubAction(0x131, "(Ground)Whip Loop(?)"),
		new SubAction(0x132, "(Air)Whip Start"),
		new SubAction(0x133, "(Air)Whip End"),
		new SubAction(0x134, "(Air)Whip Loop(?)"),
		new SubAction(0x135, "(Ground)Up-B Start"),
		new SubAction(0x136, "(Ground)Up-B"),
		new SubAction(0x137, "(Air)Up-B Start"),
		new SubAction(0x138, "(Air)Up-B"),
		new SubAction(0x139, "(Ground)Transform Animation(?)"),
		new SubAction(0x13A, "(Ground)Transform(?)"),
		new SubAction(0x13B, "(Air)Transform Animation(?)"),
		new SubAction(0x13C, "(Air)Transform(?)"),
	};
	
	public static SubAction[] yoshiSpecialMoves = {
		new SubAction(0x127, "SpecialN1"),
		new SubAction(0x128, "SpecialN1(2)"),
		new SubAction(0x129, "SpecialN2"),
		new SubAction(0x12A, "SpecialAirN1"),
		new SubAction(0x12B, "SpecialAirN1(2)"),
		new SubAction(0x12C, "SpecialAirN2"),
		new SubAction(0x12D, "(Ground)Egg Roll Start"),
		new SubAction(0x12E, "(Ground)Egg Roll Loop"),
		new SubAction(0x12F, "(Ground)Egg Roll Loop(2)"),
		new SubAction(0x130, "(Ground)Egg Roll End"),
		new SubAction(0x131, "(Air)Egg Roll Start"),
		new SubAction(0x132, "(Air)Egg Roll Loop"),
		new SubAction(0x133, "(Air)Egg Roll Loop(2)"),
		new SubAction(0x134, "(Air)Egg Roll End"),
		new SubAction(0x135, "Up-B"),
		new SubAction(0x136, "(Ground)Down-B"),
		new SubAction(0x137, "(Air)Down-B"),
		new SubAction(0x139, "(Air)Down-B land"),
		new SubAction(0x138, "Ground Pound Landing"),
	};
	
	public static SubAction[] zeldaSpecialMoves = {
		new SubAction(0x127, "(Ground)Nayru's Love"),
		new SubAction(0x128, "(Air)Nayru's Love"),
		new SubAction(0x129, "(Ground)Din's Fire Start"),
		new SubAction(0x12A, "(Ground)Din's Fire Loop"),
		new SubAction(0x12B, "(Ground)Din's Fire End"),
		new SubAction(0x12C, "(Air)Din's Fire Start"),
		new SubAction(0x12D, "(Air)Din's Fire Loop"),
		new SubAction(0x12E, "(Air)Din's Fire End"),
		new SubAction(0x12F, "(Ground)Farore's Wind Start"),
		new SubAction(0x130, "(Ground)Farore's Wind"),
		new SubAction(0x131, "(Air)Farore's Wind Start"),
		new SubAction(0x132, "(Air)Farore's Wind"),
		new SubAction(0x133, "(Ground)Transform Animation(?)"),
		new SubAction(0x134, "(Ground)Transform(?)"),
		new SubAction(0x135, "(Air)Transform Animation(?)"),
		new SubAction(0x136, "(Air)Transform(?)"),
	};
	
	//Quick and dirty, but it works and I doubt this will need to be changed later down the line.
	public static SubAction[] getListForCharacter(int selected)
	{
		SubAction[] sb = ganonfalconSpecialMoves;
		
		sb = movesToCharacterMap.get(Character.characters[selected]);
		
		return sb;
	}
	
	
	public static SpecialMoveAttribute[] getSpecialAttributesForCharacter(int selected) {
		
		return SpecialMovesList.specialAttributesToCharacterMap.get(Character.characters[MeleeEdit.selected]);
	}
}
