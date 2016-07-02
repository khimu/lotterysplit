package com.share.lottery.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "lotto_game")
public class LottoGame extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Integer version;
	
	private String state;
	
	/*
	 * name of lotto game
	 */
	private String gameName;
	
	/*
	 * validation engine
	 */
	private String lottoGameEngineName;
	
	private String winningAmount;
	
	private String prevDrawingDate;
	
	private String nextDrawingDate;
	
	private String numbers;
	
	private String link;

	/*
	 * Every day
	 * Every week
	 * 
	 * Frequency in days
	 */
	private Integer frequency;
	
	private String logo;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name="game_name")
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	@Column(name="lotto_game_engine_name")
	public String getLottoGameEngineName() {
		return lottoGameEngineName;
	}

	public void setLottoGameEngineName(String lottoGameEngineName) {
		this.lottoGameEngineName = lottoGameEngineName;
	}

	@Column(name="winning_amount")
	public String getWinningAmount() {
		return winningAmount;
	}

	public void setWinningAmount(String winningAmount) {
		this.winningAmount = winningAmount;
	}

	@Column(name="prev_drawing_date")
	public String getPrevDrawingDate() {
		return prevDrawingDate;
	}

	public void setPrevDrawingDate(String prevDrawingDate) {
		this.prevDrawingDate = prevDrawingDate;
	}

	@Column(name="next_drawing_date")
	public String getNextDrawingDate() {
		return nextDrawingDate;
	}

	public void setNextDrawingDate(String nextDrawingDate) {
		this.nextDrawingDate = nextDrawingDate;
	}

	@Column(name="numbers")
	public String getNumbers() {
		return numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	@Column(name="link")
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Column(name = "frequency")
	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	
	@Lob
	@Column(name = "logo")
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
