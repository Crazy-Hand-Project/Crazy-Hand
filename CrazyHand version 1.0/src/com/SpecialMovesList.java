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
		new SubAction(0x127, "(Ground)Neutral-B Start"),
		new SubAction(0x128, "(Ground)Neutral-B Loop"),
		new SubAction(0x129, "(Ground)Neutral-B End"),
		new SubAction(0x12A, "(Air)Neutral-B Start"),
		new SubAction(0x12B, "(Air)Neutral-B Loop"),
		new SubAction(0x12C, "(Air)Neutral-B End"),
		new SubAction(0x12D, "(Ground)Side-B Start"),
		new SubAction(0x12E, "(Ground)Side-B Hit"),//One is forward, other is behind?
		new SubAction(0x12F, "(Ground)Side-B Hit(2)"),//One is forward, other is behind?
		new SubAction(0x130, "(Ground)Side-B End Forward"),
		new SubAction(0x131, "(Ground)Side-B End Backward"),
		new SubAction(0x132, "(Air)Side-B Start"),
		new SubAction(0x133, "(Air)Side-B Hit"),//One is forward, other is behind?
		new SubAction(0x134, "(Air)Side-B Hit(2)"),//One is forward, other is behind?
		new SubAction(0x135, "(Air)Side-B End Forward"),
		new SubAction(0x136, "(Air)Side-B End Backward"),
		new SubAction(0x137, "(Ground)Up-B"),
		new SubAction(0x138, "(Air)Up-B"),
		new SubAction(0x139, "(Ground)Down-B"),
		new SubAction(0x13A, "(Air)Down-B"),
		new SubAction(0x13B, "Down-B Landing")
	};
	
	public static SubAction[] ganonfalconSpecialMoves = {
		new SubAction(0x12D, "(Ground)Neutral-B"),
		new SubAction(0x12E, "(Air)Neutral-B"),
		new SubAction(0x12F, "(Ground)Side-B Start"),
		new SubAction(0x130, "(Ground)Side-B"),
		new SubAction(0x131, "(Air)Side-B Start"),
		new SubAction(0x132, "(Air)Side-B"),
		new SubAction(0x133, "(Ground)Up-B"),
		new SubAction(0x134, "(Air)Up-B"),
		new SubAction(0x135, "Up-B Hold"),
		new SubAction(0x136, "Up-B Release"),
		new SubAction(0x137, "(Ground)Down-B"),
		new SubAction(0x138, "(Ground)Down-B End"),
		new SubAction(0x139, "(Air)Down-B"),
		new SubAction(0x13A, "(Air)Down-B End"),
		new SubAction(0x13B, "(Ground)Down-B End In-Air"),
		new SubAction(0x13C, "(Air)Down-B End In-Air"),
		
		//Data isn't being read correctly for this pointer
		//It may have something to do with FileIO and how it handles
		//the offset start/end of each character, and the data for this
		//may be outside the bounds set in Character.
		//new SubAction(0x13D, "b'Up-BThrow"),
	};
	
	public static SubAction[] donkeyKongSpecialMoves = {
		new SubAction(0x13F, "(Ground)Neutral-B Start"),
		new SubAction(0x140, "(Ground)Neutral-B Loop"),
		new SubAction(0x141, "(Ground)Neutral-B Cancel"),
		new SubAction(0x142, "(Ground)Neutral-B"),
		new SubAction(0x143, "(Ground)Neutral-B(2)"),
		new SubAction(0x144, "(Air)Neutral-B Start"),
		new SubAction(0x145, "(Air)Neutral-B Loop"),
		new SubAction(0x146, "(Air)Neutral-B Cancel"),
		new SubAction(0x147, "(Air)Neutral-B"),
		new SubAction(0x148, "(Air)Neutral-B(2)"),
		new SubAction(0x149, "(Ground)Side-B"),
		new SubAction(0x14A, "(Air)Side-B"),
		new SubAction(0x14B, "(Ground)Up-B"),
		new SubAction(0x14C, "(Air)Up-B"),
		new SubAction(0x14D, "Down-B Start"),
		new SubAction(0x14E, "Down-B Loop"),
		new SubAction(0x14F, "Down-B End"),
		new SubAction(0x150, "Down-B End(2)"),
	};
	
	public static SubAction[] spacieSpecialMoves = {
		new SubAction(0x127, "(Ground)Neutral-B Start"),
		new SubAction(0x128, "(Ground)Neutral-B Loop"),
		new SubAction(0x129, "(Ground)Neutral-B End"),
		new SubAction(0x12A, "(Air)Neutral-B Start"),
		new SubAction(0x12B, "(Air)Neutral-B Loop"),
		new SubAction(0x12C, "(Air)Neutral-B End"),
		new SubAction(0x12D, "(Ground)Side-B Start"),
		new SubAction(0x12E, "(Ground)Side-B"),
		new SubAction(0x12F, "(Ground)Side-B End"),
		new SubAction(0x130, "(Air)Side-B Start"),
		new SubAction(0x131, "(Air)Side-B"),
		new SubAction(0x132, "(Air)Side-B End"),
		new SubAction(0x133, "(Ground)Up-B Hold"),
		new SubAction(0x134, "(Air)Up-B Hold"),
		new SubAction(0x135, "Up-B"),
		new SubAction(0x136, "Up-B Landing"),
		new SubAction(0x137, "Up-B Fall"),
		new SubAction(0x138, "Up-B Bound"),
		new SubAction(0x139, "(Ground)Down-B Start"),
		new SubAction(0x13A, "(Ground)Down-B Loop"),
		new SubAction(0x13B, "(Ground)Down-B Hit"),
		new SubAction(0x13C, "(Ground)Down-B End"),
		new SubAction(0x13D, "(Air)Down-B Start"),
		new SubAction(0x13E, "(Air)Down-B Loop"),
		new SubAction(0x13F, "(Air)Down-B Hit"),
		new SubAction(0x140, "(Air)Down-B End"),
		
		//There's more entries after this, starting with AppealS
		//It might be a taunt?
		
		//new SubAction(0x141, "b'Special"),
	};
	
	public static SubAction[] gnwSpecialMoves = {
		new SubAction(0x127, "(Ground)Neutral-B"),
		new SubAction(0x128, "(Air)Neutral-B"),
		new SubAction(0x129, "(Ground)Side-B (1)"),
		new SubAction(0x12A, "(Ground)Side-B (2)"),
		new SubAction(0x12B, "(Ground)Side-B (3)"),
		new SubAction(0x12C, "(Ground)Side-B (4)"),
		new SubAction(0x12D, "(Ground)Side-B (5)"),
		new SubAction(0x12E, "(Ground)Side-B (6)"),
		new SubAction(0x12F, "(Ground)Side-B (7)"),
		new SubAction(0x130, "(Ground)Side-B (8)"),
		new SubAction(0x131, "(Ground)Side-B (9)"),
		new SubAction(0x132, "(Air)Side-B (1)"),
		new SubAction(0x133, "(Air)Side-B (2)"),
		new SubAction(0x134, "(Air)Side-B (3)"),
		new SubAction(0x135, "(Air)Side-B (4)"),
		new SubAction(0x136, "(Air)Side-B (5)"),
		new SubAction(0x137, "(Air)Side-B (6)"),
		new SubAction(0x138, "(Air)Side-B (7)"),
		new SubAction(0x139, "(Air)Side-B (8)"),
		new SubAction(0x13A, "(Air)Side-B (9)"),
		new SubAction(0x13B, "(Ground)Up-B"),
		new SubAction(0x13C, "(Air)Up-B"),
		new SubAction(0x13D, "(Ground)Down-B"),
		new SubAction(0x13E, "(Ground)Down-B Absorb"),
		new SubAction(0x13F, "(Ground)Down-B Shoot"),
		new SubAction(0x140, "(Air)Down-B"),
		new SubAction(0x141, "(Air)Down-B Absorb"),
		new SubAction(0x142, "(Air)Down-B Shoot"),
	};
	
	public static SubAction[] popoNanaSpecialMoves = {
		new SubAction(0x127, "(Ground)Neutral-B"),
		new SubAction(0x128, "(Air)Neutral-B"),
		new SubAction(0x129, "(Ground)Side-B (1)"),
		new SubAction(0x12A, "(Ground)Side-B (2)"),
		new SubAction(0x12B, "(Air)Side-B (1)"),
		new SubAction(0x12C, "(Air)Side-B (2)"),
		new SubAction(0x12D, "(Ground)Up-B Start"),
		new SubAction(0x12E, "(Ground)Up-B Throw(1)"),
		new SubAction(0x12F, "(Ground)Up-B Throw(2)"),
		new SubAction(0x130, "(Ground)Up-B Throw(3)"),
		new SubAction(0x131, "(Ground)Up-B Throw(4)"),
		new SubAction(0x132, "(Air)Up-B Start"),
		new SubAction(0x133, "(Air)Up-B Throw(1)"),
		new SubAction(0x134, "(Air)Up-B Throw(2)"),
		new SubAction(0x135, "(Air)Up-B Throw(3)"),
		new SubAction(0x136, "(Air)Up-B Throw(4)"),
		new SubAction(0x137, "(Ground)Down-B"),
		new SubAction(0x138, "(Air)Down-B"),
		
		//More unnamed entries below specialAirLw, I'd assume it has something to do
		//with syncing Nana to Popo or vice versa
	};
	
	public static SubAction[] jigglypuffSpecialMoves = {
		new SubAction(0x12C, "(Ground)Neutral-B Start (R)"),
		new SubAction(0x12D, "(Ground)Neutral-B Start (L)"),
		new SubAction(0x12E, "(Ground)Neutral-B(1)"),
		new SubAction(0x12F, "(Ground)Neutral-B(2)"),
		new SubAction(0x130, "(Ground)Neutral-B(3)"),
		new SubAction(0x131, "(Ground)Neutral-B(4)"),
		new SubAction(0x132, "(Ground)Neutral-B End (R)"),
		new SubAction(0x133, "(Ground)Neutral-B End (L)"),
		
		new SubAction(0x12C, "(Air)Neutral-B Start (R)"),
		new SubAction(0x12D, "(Air)Neutral-B Start (L)"),
		new SubAction(0x12E, "(Air)Neutral-B(1)"),
		new SubAction(0x12F, "(Air)Neutral-B(2)"),
		new SubAction(0x130, "(Air)Neutral-B(3)"),
		new SubAction(0x131, "(Air)Neutral-B(4)"),
		new SubAction(0x132, "(Air)Neutral-B End (R)"),
		new SubAction(0x133, "(Air)Neutral-B End (L)"),
		new SubAction(0x13C, "Neutral-B"),
		new SubAction(0x13D, "(Ground)Side-B"),
		new SubAction(0x13E, "(Air)Side-B"),
		new SubAction(0x13F, "(Ground)Up-B (L)"),
		new SubAction(0x140, "(Air)Up-B (L)"),
		new SubAction(0x141, "(Ground)Up-B (R)"),
		new SubAction(0x142, "(Air)Up-B (R)"),
		new SubAction(0x143, "(Ground)Down-B (L)"),
		new SubAction(0x144, "(Air)Down-B (L)"),
		new SubAction(0x145, "(Ground)Down-B (R)"),
		new SubAction(0x146, "(Air)Down-B (R)"),
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
		new SubAction(0x142, "b'(Ground)Side-B"),
		new SubAction(0x143, "b'SpecialAirS"),
		new SubAction(0x144, "b'Up-B1"),
		new SubAction(0x145, "b'Up-B2"),
		new SubAction(0x146, "b'Up-B3"),
		new SubAction(0x147, "b'Up-B4"),
		new SubAction(0x148, "b'(Air)Up-B1"),
		new SubAction(0x149, "b'(Air)Up-B2"),
		new SubAction(0x14A, "b'(Air)Up-B3"),
		new SubAction(0x14B, "b'(Air)Up-B4"),
		new SubAction(0x14C, "b'(Ground)Down-B 1"),
		new SubAction(0x14D, "b'(Ground)Down-B 2"),
		new SubAction(0x14E, "b'(Ground)Down-B 2(2)"),
		new SubAction(0x14F, "b'SpecialAirLw1"),
		new SubAction(0x150, "b'SpecialAirLw2"),
		new SubAction(0x151, "b'SpecialAirLw2(2)"),
		new SubAction(0x152, "b'MrSpecialN"),
		new SubAction(0x153, "b'Mr(Air)Neutral-B"),
		new SubAction(0x154, "b'LkSpecialNStart"),
		new SubAction(0x155, "b'LkSpeciaNLoop"),
		new SubAction(0x156, "b'LkSpecialNEnd"),
		new SubAction(0x157, "b'Lk(Air)Neutral-BStart"),
		new SubAction(0x158, "b'Lk(Air)Neutral-BLoop"),
		new SubAction(0x159, "b'Lk(Air)Neutral-BEnd"),
		new SubAction(0x15A, "b'SsSpecialNStart"),
		new SubAction(0x15B, "b'SsSpecialNHold"),
		new SubAction(0x15C, "b'SsSpecialNCancel"),
		new SubAction(0x15D, "b'SsSpecialN"),
		new SubAction(0x15E, "b'Ss(Air)Neutral-BStart"),
		new SubAction(0x15F, "b'Ss(Air)Neutral-B"),
		new SubAction(0x160, "b'Ys(Air)Neutral-B1"),
		new SubAction(0x161, "b'Ys(Air)Neutral-B1(2)"),
		new SubAction(0x162, "b'Ys(Air)Neutral-B2"),
		new SubAction(0x163, "b'FxSpecialNStart"),
		new SubAction(0x164, "b'FxSpecialNLoop"),
		new SubAction(0x165, "b'FxSpecialNEnd"),
		new SubAction(0x166, "b'Fx(Air)Neutral-BStart"),
		new SubAction(0x167, "b'Fx(Air)Neutral-BLoop"),
		new SubAction(0x168, "b'Fx(Air)Neutral-BEnd"),
		new SubAction(0x169, "b'PkSpecialN"),
		new SubAction(0x16A, "b'Pk(Air)Neutral-B"),
		new SubAction(0x16B, "b'LgSpecialN"),
		new SubAction(0x16C, "b'Lg(Air)Neutral-B"),
		new SubAction(0x16D, "b'CaSpecialN"),
		new SubAction(0x16E, "b'Ca(Air)Neutral-B"),
		new SubAction(0x16F, "b'NsSpecialNStart"),
		new SubAction(0x170, "b'NsSpecialNHold"),
		new SubAction(0x171, "b'NsSpecialNHold(2)"),
		new SubAction(0x172, "b'NsSpecialNEnd"),
		new SubAction(0x173, "b'Ns(Air)Neutral-BStart"),
		new SubAction(0x174, "b'Ns(Air)Neutral-BHold"),
		new SubAction(0x175, "b'Ns(Air)Neutral-BHold(2)"),
		new SubAction(0x176, "b'Ns(Air)Neutral-BEnd"),
		new SubAction(0x177, "b'KpSpecialNStart"),
		new SubAction(0x178, "b'KpSpecialN"),
		new SubAction(0x179, "b'KpSpecialNEnd"),
		new SubAction(0x17A, "b'Kp(Air)Neutral-BStart"),
		new SubAction(0x17B, "b'Kp(Air)Neutral-B"),
		new SubAction(0x17C, "b'Kp(Air)Neutral-BEnd"),
		new SubAction(0x17D, "b'Pe(Ground)Down-B "),
		new SubAction(0x17E, "b'Pe(Ground)Down-B Hit"),
		new SubAction(0x17F, "b'PeSpecialAirLw"),
		new SubAction(0x180, "b'PeSpecialAirLwHit"),
		new SubAction(0x181, "b'PpSpecialN"),
		new SubAction(0x182, "b'Pp(Air)Neutral-B"),
		new SubAction(0x183, "b'DkSpecialNStart"),
		new SubAction(0x184, "b'DkSpecialNLoop"),
		new SubAction(0x185, "b'DkSpecialNCancel"),
		new SubAction(0x186, "b'DkSpecialN"),
		new SubAction(0x187, "b'DkSpecialN(2)"),
		new SubAction(0x188, "b'Dk(Air)Neutral-BStart"),
		new SubAction(0x189, "b'Dk(Air)Neutral-BLoop"),
		new SubAction(0x18A, "b'Dk(Air)Neutral-BCancel"),
		new SubAction(0x18B, "b'Dk(Air)Neutral-B"),
		new SubAction(0x18C, "b'Dk(Air)Neutral-B(2)"),
		new SubAction(0x18D, "b'ZdSpecialN"),
		new SubAction(0x18E, "b'Zd(Air)Neutral-B"),
		new SubAction(0x18F, "b'SkSpecialNStart"),
		new SubAction(0x190, "b'SkSpecialNLoop"),
		new SubAction(0x191, "b'SkSpecialNCancel"),
		new SubAction(0x192, "b'SkSpecialNEnd"),
		new SubAction(0x193, "b'Sk(Air)Neutral-BStart"),
		new SubAction(0x194, "b'Sk(Air)Neutral-BLoop"),
		new SubAction(0x195, "b'Sk(Air)Neutral-BEnd"),
		new SubAction(0x196, "b'PrSpecialNStartR"),
		new SubAction(0x197, "b'PrSpecialNStartL"),
		new SubAction(0x198, "b'PrSpecialN"),
		new SubAction(0x199, "b'PrSpecialN(2)"),
		new SubAction(0x19A, "b'PrSpecialN(3)"),
		new SubAction(0x19B, "b'PrSpecialN(4)"),
		new SubAction(0x19C, "b'PrSpecialNEndR"),
		new SubAction(0x19D, "b'PrSpecialNEndL"),
		new SubAction(0x19E, "b'Pr(Air)Neutral-BStartR"),
		new SubAction(0x19F, "b'Pr(Air)Neutral-BStartL"),
		new SubAction(0x1A0, "b'PrSpecialN(5)"),
		new SubAction(0x1A1, "b'PrSpecialN(6)"),
		new SubAction(0x1A2, "b'PrSpecialN(7)"),
		new SubAction(0x1A3, "b'Pr(Air)Neutral-BEndR"),
		new SubAction(0x1A4, "b'Pr(Air)Neutral-BEndR(2)"),
		new SubAction(0x1A5, "b'PrSpecialN(8)"),
		new SubAction(0x1A6, "b'MsSpecialNStart"),
		new SubAction(0x1A7, "b'MsSpecialNLoop"),
		new SubAction(0x1A8, "b'MsSpecialNEnd"),
		new SubAction(0x1A9, "b'MsSpecialNEnd(2)"),
		new SubAction(0x1AA, "b'Ms(Air)Neutral-BStart"),
		new SubAction(0x1AB, "b'Ms(Air)Neutral-BLoop"),
		new SubAction(0x1AC, "b'Ms(Air)Neutral-BEnd"),
		new SubAction(0x1AD, "b'Ms(Air)Neutral-BEnd(2)"),
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
		new SubAction(0x127, "(Ground)Neutral-B"),
		new SubAction(0x128, "(Air)Neutral-B"),
		new SubAction(0x129, "(Ground)Side-B Start"),
		new SubAction(0x12A, "(Ground)Side-B Charge"),
		new SubAction(0x12B, "(Ground)Side-B(1)"),
		new SubAction(0x12C, "(Ground)Side-B(2)"),
		new SubAction(0x12D, "(Ground)Side-B(3)"),
		new SubAction(0x12E, "(Ground)Side-B End"),
		new SubAction(0x12F, "(Air)Side-B Start"),
		new SubAction(0x130, "(Air)Side-B Charge"),
		//Most likely, these two Side-B entries aren't air versions of the move, but are entries for misfire.
		//We can re-label them later once we're sure.
		new SubAction(0x131, "(Air)Side-B(1)"),
		new SubAction(0x132, "(Air)Side-B(2)"),
		new SubAction(0x133, "(Air)Side-B End"),
		new SubAction(0x134, "(Ground)Up-B"),
		new SubAction(0x135, "(Air)Up-B"),
		new SubAction(0x136, "(Ground)Down-B"),
		new SubAction(0x137, "(Air)Down-B"),
	};
	
	public static SubAction[] linkSpecialMoves = {
		new SubAction(0x128, "(Ground)Neutral-B Start"),
		new SubAction(0x129, "(Ground)Neutral-B Charge"),
		new SubAction(0x12A, "(Ground)Neutral-B End"),
		new SubAction(0x12B, "(Air)Neutral-B Start"),
		new SubAction(0x12C, "(Air)Neutral-B Charge"),
		new SubAction(0x12D, "(Air)Neutral-B End"),
		new SubAction(0x12E, "(Ground)Side-B Throw"),
		new SubAction(0x12F, "(Ground)Side-B Catch"),
		new SubAction(0x130, "(Ground)Side-B Throw(Angled)"),
		new SubAction(0x131, "(Air)Side-B Throw"),
		new SubAction(0x132, "(Air)Side-B Catch"),
		new SubAction(0x133, "(Air)Side-B Throw(Angled)"),
		new SubAction(0x134, "(Ground)Up-B"),
		new SubAction(0x135, "(Air)Up-B"),
		new SubAction(0x136, "(Ground)Down-B"),
		new SubAction(0x137, "(Air)Down-B"),
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
		new SubAction(0x127, "(Ground)Neutral-B"),
		new SubAction(0x128, "(Air)Neutral-B"),
		new SubAction(0x129, "(Ground)Side-B"),
		new SubAction(0x12a, "(Air)Side-B"),
		new SubAction(0x12b, "(Ground)Up-B"),
		new SubAction(0x12c, "(Air)Up-B"),
		new SubAction(0x12d, "(Ground)Down-B"),
		new SubAction(0x12e, "(Air)Down-B")
	};
	
	public static SubAction[] feSwordsmanSpecialMoves = {
		new SubAction(0x127, "Neutral-B Start"),
		new SubAction(0x128, "Neutral-B Hold"),
		new SubAction(0x129, "Neutral-B End"),
		new SubAction(0x12A, "Neutral-B End(2)"),
		new SubAction(0x12B, "(Air)Neutral-B Start"),
		new SubAction(0x12C, "(Air)Neutral-B Hold"),
		new SubAction(0x12D, "(Air)Neutral-B End"),
		new SubAction(0x12E, "(Air)Neutral-B End(2)"),
		new SubAction(0x12F, "Side-B Start"),
		new SubAction(0x130, "(Step 1)Side-B (Side-Up)"),
		new SubAction(0x131, "(Step 1)Side-B (Side-Down)"),
		new SubAction(0x132, "(Step 2)Side-B (Side-Up)"),
		new SubAction(0x133, "(Step 2)Side-B (Side)"),
		new SubAction(0x134, "(Step 2)Side-B (Side-Down)"),
		new SubAction(0x135, "(Step 3)Side-B (Side-Up)"),
		new SubAction(0x136, "(Step 3)Side-B (Side)"),
		new SubAction(0x137, "(Step 3)Side-B (Side-Down)"),
		new SubAction(0x138, "(Air)Side-B Start"),
		new SubAction(0x139, "(Air, Step 1)Side-B (Side-Up)"),
		new SubAction(0x13A, "(Air, Step 1)Side-B (Side-Down)"),
		new SubAction(0x13B, "(Air, Step 2)Side-B (Side-Up)"),
		new SubAction(0x13C, "(Air, Step 2)Side-B (Side)"),
		new SubAction(0x13D, "(Air, Step 2)Side-B (Side-Down)"),
		new SubAction(0x13E, "(Air, Step 3)Side-B (Side-Up)"),
		new SubAction(0x13F, "(Air, Step 3)Side-B (Side)"),
		new SubAction(0x140, "(Air, Step 3)Side-B (Side-Down)"),
		new SubAction(0x141, "(Ground)Up-B"),
		new SubAction(0x142, "(Air)Up-B"),
		new SubAction(0x143, "(Ground)Down-B"),
		new SubAction(0x144, "(Ground)Down-B Counter"),
		new SubAction(0x145, "(Air)Down-B"),
		new SubAction(0x146, "(Air)Down-B Counter"),
	};
	
	
	public static SubAction[] mewtwoSpecialMoves = {
		new SubAction(0x127, "(Ground)Neutral-B Start"),
		new SubAction(0x128, "(Ground)Neutral-B Loop"),
		new SubAction(0x129, "(Ground)Neutral-B Loop(2)"),
		new SubAction(0x12A, "(Ground)Neutral-B Cancel"),
		new SubAction(0x12B, "(Ground)Neutral-B End"),
		new SubAction(0x12C, "(Air)Neutral-B Start"),
		new SubAction(0x12E, "(Air)Neutral-B Loop"),
		new SubAction(0x12D, "(Air)Neutral-B Loop(2)"),
		new SubAction(0x12F, "(Air)Neutral-B Cancel"),
		new SubAction(0x130, "(Air)Neutral-B End"),
		new SubAction(0x131, "(Ground)Side-B"),
		new SubAction(0x132, "(Air)Side-B"),
		new SubAction(0x133, "(Ground)Up-B Start"),
		new SubAction(0x134, "(Ground)Up-B"),
		new SubAction(0x135, "Up-B Lost"),
		new SubAction(0x136, "(Air)Up-B Start"),
		new SubAction(0x137, "(Air)Up-B"),
		new SubAction(0x138, "(Ground)Down-B "),
		new SubAction(0x139, "(Air)Down-B"),
	};
	
	public static SubAction[] nessSpecialMoves = {
		new SubAction(0x12B, "(Ground)Neutral-B Start"),
		new SubAction(0x12C, "(Ground)Neutral-B Hold"),
		new SubAction(0x12D, "(Ground)Neutral-B Hold(2)"),
		new SubAction(0x12E, "(Ground)Neutral-B End"),
		new SubAction(0x12F, "(Air)Neutral-BStart"),
		new SubAction(0x130, "(Air)Neutral-B Hold"),
		new SubAction(0x131, "(Air)Neutral-B Hold(2)"),
		new SubAction(0x132, "(Air)Neutral-B End"),
		new SubAction(0x133, "(Ground)Side-B"),
		new SubAction(0x134, "(Air)Side-B"),
		new SubAction(0x135, "(Ground)Up-B Start"),
		new SubAction(0x136, "(Ground)Up-B Hold"),
		new SubAction(0x137, "(Ground)Up-B End"),
		new SubAction(0x138, "Up-B"),
		new SubAction(0x139, "(Air)Up-B Start"),
		new SubAction(0x13A, "(Air)Up-B Hold"),
		new SubAction(0x13B, "(Air)Up-B End"),
		new SubAction(0x13C, "Up-B(2)"),
		new SubAction(0x13D, "DamageFall"),
		new SubAction(0x13E, "(Ground)Down-B Start"),
		new SubAction(0x13F, "(Ground)Down-B Hold"),
		new SubAction(0x140, "(Ground)Down-B Hit"),
		new SubAction(0x141, "(Ground)Down-B End"),
		new SubAction(0x142, "(Air)Down-B Start"),
		new SubAction(0x143, "(Air)Down-B Hold"),
		new SubAction(0x144, "(Air)Down-B Hit"),
		new SubAction(0x145, "(Air)Down-B End"),
	};
	
	public static SubAction[] peachSpecialMoves = {
		new SubAction(0x138, "(Ground)Neutral-B"),
		new SubAction(0x139, "(Ground)Neutral-B Counter"),
		new SubAction(0x13A, "(Air)Neutral-B"),
		new SubAction(0x13B, "(Air)Neutral-B Counter"),
		new SubAction(0x12E, "(Ground)Side-B Start"),
		new SubAction(0x12F, "(Ground)Side-B Miss"),
		new SubAction(0x130, "Side-B Hit"),
		new SubAction(0x131, "(Air)Side-B Start"),
		new SubAction(0x132, "(Air)Side-B Miss"),
		new SubAction(0x133, "Side-B hit wall(?)"),
		new SubAction(0x134, "(Ground)Up-B Start"),
		new SubAction(0x135, "(Ground)Up-B End"),
		new SubAction(0x136, "(Air)Up-B Start"),
		new SubAction(0x137, "(Air)Up-B End"),
		new SubAction(0x13C, "Open Parasol"),
		new SubAction(0x13D, "Close Parasol"),
		new SubAction(0x12D, "Down-B"),
	};
	
	public static SubAction[] pikaSpecialMoves = {
		new SubAction(0x127, "(Ground)Neutral-B"),
		new SubAction(0x128, "(Air)Neutral-B"),
		new SubAction(0x129, "(Ground)Side-B Start"),
		new SubAction(0x12A, "(Ground)Side-B Hold"),
		new SubAction(0x12B, "(Ground)Side-B"),
		new SubAction(0x12C, "(Ground)Side-B(2)"),
		new SubAction(0x12D, "(Ground)Side-B End"),
		new SubAction(0x12E, "(Air)Side-B Start"),
		new SubAction(0x12F, "(Air)Side-B Hold"),
		new SubAction(0x130, "(Air)Side-B(2)"),
		new SubAction(0x131, "(Air)Side-B End"),
		new SubAction(0x132, "(Ground)Up-B Start"),
		new SubAction(0x133, "(Ground)Up-B Start(2)"),
		new SubAction(0x134, "(Ground)Up-B End"),
		new SubAction(0x135, "(Air)Up-B Start"),
		new SubAction(0x136, "(Air)Up-B Start(2)"),
		new SubAction(0x137, "(Air)Up-B End"),
		new SubAction(0x138, "(Ground)Down-B Start"),
		new SubAction(0x139, "(Ground)Down-B Loop"),
		new SubAction(0x13A, "(Ground)Down-B Loop(2)"),
		new SubAction(0x13B, "(Ground)Down-B End"),
		new SubAction(0x13C, "(Air)Down-B Start"),
		new SubAction(0x13D, "(Air)Down-B Loop"),
		new SubAction(0x13E, "(Air)Down-B Loop(2)"),
		new SubAction(0x13F, "(Air)Down-B End"),
	};
	
	public static SubAction[] samusSpecialMoves = {
		new SubAction(0x127, "(Ground)Down-B?"),
		new SubAction(0x128, "(Air)Down-B?"),
		new SubAction(0x129, "Neutral-BStart"),
		new SubAction(0x12A, "Neutral-B Hold"),
		new SubAction(0x12B, "Neutral-B Cancel"),
		new SubAction(0x12C, "Neutral-B"),
		new SubAction(0x12D, "(Air)Neutral-B Start"),
		new SubAction(0x12E, "(Air)Neutral-B"),
		new SubAction(0x12F, "(Ground)Homing Rocket(?)"),
		new SubAction(0x130, "(Ground)Hard Rocket(?)"),
		new SubAction(0x131, "(Air)Homing Rocket(?)"),
		new SubAction(0x132, "(Air)Hard Rocket(?)"),
		new SubAction(0x133, "(Ground)Screw Attack"),
		new SubAction(0x134, "(Air)Screw Attack"),
		new SubAction(0x135, "(Ground)Down-B?"),
		new SubAction(0x136, "(Air)Down-B?"),
		//Two more entries below SpecialAirLw, AirCatch and AirCatchHit.
		//I'd assume that's zair.
		//Maybe consider creating a new list for misc. subactions for cases like this.
	};
	
	public static SubAction[] shiekSpecialMoves = {
		new SubAction(0x127, "(Ground)Neutral-B Start"),
		new SubAction(0x128, "(Ground)Neutral-B"),
		new SubAction(0x129, "(Ground)Neutral-B Cancel"),
		new SubAction(0x12A, "(Ground)Shoot Needles(?)"),
		new SubAction(0x12B, "(Air)Neutral-B Start"),
		new SubAction(0x12C, "(Air)Neutral-B"),
		new SubAction(0x12D, "(Air)Neutral-B Cancel"),
		new SubAction(0x12E, "(Air)Shoot Needles(?)"),
		new SubAction(0x12F, "(Ground)Side-B Start"),
		new SubAction(0x130, "(Ground)Side-B End"),
		new SubAction(0x131, "(Ground)Side-B Loop(?)"),
		new SubAction(0x132, "(Air)Side-B Start"),
		new SubAction(0x133, "(Air)Side-B End"),
		new SubAction(0x134, "(Air)Side-B Loop(?)"),
		new SubAction(0x135, "(Ground)Up-B Start"),
		new SubAction(0x136, "(Ground)Up-B"),
		new SubAction(0x137, "(Air)Up-B Start"),
		new SubAction(0x138, "(Air)Up-B"),
		new SubAction(0x139, "(Ground)Down-B Animation(?)"),
		new SubAction(0x13A, "(Ground)Down-B(?)"),
		new SubAction(0x13B, "(Air)Down-B Animation(?)"),
		new SubAction(0x13C, "(Air)Down-B(?)"),
	};
	
	public static SubAction[] yoshiSpecialMoves = {
		new SubAction(0x127, "(Ground)Neutral-B"),
		new SubAction(0x128, "(Ground)Neutral-B(2)"),
		new SubAction(0x129, "(Ground)Neutral-B(3)"),
		new SubAction(0x12A, "(Air)Neutral-B"),
		new SubAction(0x12B, "(Air)Neutral-B(2)"),
		new SubAction(0x12C, "(Air)Neutral-B(3)"),
		new SubAction(0x12D, "(Ground)Side-B Start"),
		new SubAction(0x12E, "(Ground)Side-B Loop"),
		new SubAction(0x12F, "(Ground)Side-B Loop(2)"),
		new SubAction(0x130, "(Ground)Side-B End"),
		new SubAction(0x131, "(Air)Side-B Start"),
		new SubAction(0x132, "(Air)Side-B Loop"),
		new SubAction(0x133, "(Air)Side-B Loop(2)"),
		new SubAction(0x134, "(Air)Side-B End"),
		new SubAction(0x135, "Up-B"),
		new SubAction(0x136, "(Ground)Down-B"),
		new SubAction(0x137, "(Air)Down-B"),
		new SubAction(0x139, "(Air)Down-B land"),
		new SubAction(0x138, "Down-B Landing(?)"),
	};
	
	public static SubAction[] zeldaSpecialMoves = {
		new SubAction(0x127, "(Ground)Neutral-B"),
		new SubAction(0x128, "(Air)Neutral-B"),
		new SubAction(0x129, "(Ground)Side-B Start"),
		new SubAction(0x12A, "(Ground)Side-B Loop"),
		new SubAction(0x12B, "(Ground)Side-B End"),
		new SubAction(0x12C, "(Air)Side-B Start"),
		new SubAction(0x12D, "(Air)Side-B Loop"),
		new SubAction(0x12E, "(Air)Side-B End"),
		new SubAction(0x12F, "(Ground)Up-B Start"),
		new SubAction(0x130, "(Ground)Up-B"),
		new SubAction(0x131, "(Air)Up-B Start"),
		new SubAction(0x132, "(Air)Up-B"),
		new SubAction(0x133, "(Ground)Down-B(?)"),
		new SubAction(0x134, "(Ground)Down-B(?)"),
		new SubAction(0x135, "(Air)Down-B(?)"),
		new SubAction(0x136, "(Air)Down-B(?)"),
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
