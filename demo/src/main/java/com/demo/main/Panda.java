package com.demo.main;

public class Panda {
	private KungFu kungfu;

	public Panda(KungFu kungfu) {
		System.out.println("autowiring by constructor");
		this.kungfu = kungfu;
	}

	public KungFu getKungfu() {
		return kungfu;
	}

	public void setKungfu(KungFu kungfu) {
		this.kungfu = kungfu;
	}

}
