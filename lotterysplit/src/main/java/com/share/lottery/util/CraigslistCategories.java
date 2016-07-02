package com.share.lottery.util;

public final class CraigslistCategories {

	public static enum Category {
		sof("Software/QA/DBA etc jobs"), sss("all for sale / wanted"), ata("antiques"), ppa("appliances"), ara(
				"arts+crafts"), pta("auto parts"), baa("baby+kids"), bar(
				"barter"), haa("beauty+health"), bia("bikes"), boo("boats"), bka(
				"books"), bfa("business"), cta("cars+trucks"), ema("cd/dvd/vhs"), moa(
				"cell phones"), cla("clothing+accessories"), cba("collectibles"), sya(
				"computers"), ela("electronics"), gra("farm+garden"), zip(
				"free stuff"), fua("furniture"), gms("garage sales"), foa(
				"general for sale"), hsa("household"), wan("items wanted"), jwa(
				"jewelry"), maa("materials"), mca("motorcycles"), msa(
				"musical instruments"), pha("photo+video"), rva(
				"recreational vehicles"), sga("sporting goods"), tia("tickets"), tla(
				"tools"), taa("toys+games"), vga("video gaming"), cpg("gigs");

		private String description;

		private Category(String description) {
			this.description = description;
		}

		public String getDescription() {
			return this.description;
		}
		
	}
}
