import { element, by, ElementFinder } from 'protractor';

export class CovidComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-covid div table .btn-danger'));
  title = element.all(by.css('jhi-covid div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getText();
  }
}

export class CovidUpdatePage {
  pageTitle = element(by.id('jhi-covid-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nombretestInput = element(by.id('field_nombretest'));
  positifcasInput = element(by.id('field_positifcas'));
  dateInput = element(by.id('field_date'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setNombretestInput(nombretest: string): Promise<void> {
    await this.nombretestInput.sendKeys(nombretest);
  }

  async getNombretestInput(): Promise<string> {
    return await this.nombretestInput.getAttribute('value');
  }

  async setPositifcasInput(positifcas: string): Promise<void> {
    await this.positifcasInput.sendKeys(positifcas);
  }

  async getPositifcasInput(): Promise<string> {
    return await this.positifcasInput.getAttribute('value');
  }

  async setDateInput(date: string): Promise<void> {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput(): Promise<string> {
    return await this.dateInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class CovidDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-covid-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-covid'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
